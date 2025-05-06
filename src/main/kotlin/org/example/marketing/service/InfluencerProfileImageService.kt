package org.example.marketing.service

import io.github.oshai.kotlinlogging.KotlinLogging
import org.apache.tika.Tika
import org.example.marketing.domain.user.InfluencerProfileImage
import org.example.marketing.dto.board.response.BinaryImageDataWithType
import org.example.marketing.dto.user.request.MakeNewInfluencerProfileImageRequest
import org.example.marketing.dto.user.request.SaveInfluencerProfileImage
import org.example.marketing.exception.IllegalResourceUsageException
import org.example.marketing.exception.NotFoundInfluencerImageException
import org.example.marketing.repository.user.InfluencerProfileImageRepository
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*

@Service
class InfluencerProfileImageService(
    private val influencerProfileImageRepository: InfluencerProfileImageRepository
) {
    private val rootDirPath = "C:\\Users\\dideh\\Desktop\\Spring\\images\\user\\influencer"
    private val logger = KotlinLogging.logger {}
    fun save(
        influencerId: Long,
        meta: MakeNewInfluencerProfileImageRequest,
        file: MultipartFile
        ): InfluencerProfileImage {

        val tika = Tika()
        val realType = tika.detect(file.inputStream)
        // save file
        val dir = Paths.get(rootDirPath)
        Files.createDirectories(dir)

        val extension = when (realType) {
            "image/png" -> "png"
            "image/jpeg" -> "jpg"
            else -> "bin"
        }
        val unifiedUuid = UUID.randomUUID().toString()
        val filePath = dir.resolve("${meta.originalFileName}.$extension")

        file.inputStream.use {
            Files.copy(it, filePath, StandardCopyOption.REPLACE_EXISTING)
        }

        try {
            return transaction {
                val savedEntity = influencerProfileImageRepository.save(
                    SaveInfluencerProfileImage.of(
                        influencerId = influencerId,
                        originalFileName = meta.originalFileName,
                        unifiedCode = unifiedUuid,
                        filePath = filePath.toString(),
                        fileType = realType
                    )
                )

                InfluencerProfileImage.of(savedEntity)
            }
        } catch (ex: Exception) {
            runCatching { Files.deleteIfExists(filePath) }
            throw ex
        }
    }


    fun commit(influencerId: Long, entityId: Long): InfluencerProfileImage {
        return transaction {
            // 📌 skip legal check

            val commitedEntity = influencerProfileImageRepository.commitById(entityId)

            if (commitedEntity != null) {
                InfluencerProfileImage.of(commitedEntity)
            } else {
                throw NotFoundInfluencerImageException(
                    logics = "influencerImgSvc - commit"
                )
            }
        }

    }

    fun findByUnifiedCode(unifiedCode: String): BinaryImageDataWithType {
        return transaction {
            val targetEntity = influencerProfileImageRepository.findByUnifiedCode(unifiedCode)

            if (targetEntity == null) {
                throw NotFoundInfluencerImageException("influencerImageSvc = findByUnifiedCode")
            } else {
                val path = Paths.get(targetEntity.filePath)
                val bytes = Files.readAllBytes(path)
                val type = targetEntity.fileType
                BinaryImageDataWithType.of(
                    bytes = bytes,
                    type = type
                )
            }
        }
    }
}