package org.example.marketing.service

import org.apache.tika.Tika
import org.example.marketing.domain.user.AdvertiserProfileImage
import org.example.marketing.dto.board.response.BinaryImageDataWithType
import org.example.marketing.dto.user.request.MakeNewAdvertiserProfileImageRequest
import org.example.marketing.dto.user.request.SaveAdvertiserProfileImage
import org.example.marketing.exception.NotFoundAdvertiserImageException
import org.example.marketing.repository.user.AdvertiserProfileImageRepository
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*

@Service
class AdvertiserProfileImageService(
    private val advertiserProfileImageRepository: AdvertiserProfileImageRepository
) {
    private val rootDirPath = "C:\\Users\\dideh\\Desktop\\Spring\\images\\user\\advertiser"

    fun save(
        advertiserId: Long,
        meta: MakeNewAdvertiserProfileImageRequest,
        file: MultipartFile
    ): AdvertiserProfileImage {
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
                val savedEntity= advertiserProfileImageRepository.save(
                    SaveAdvertiserProfileImage.of(
                        advertiserId = advertiserId,
                        request = meta,
                        filePath = filePath.toString(),
                        fileType = realType,
                        unifiedCode = unifiedUuid
                    )
                )

                AdvertiserProfileImage.of(savedEntity)
            }
        } catch (ex: Exception) {
            runCatching { Files.deleteIfExists(filePath) }
            throw ex
        }
    }

    fun commit(advertiserId: Long, metaEntityId: Long): AdvertiserProfileImage {
        return transaction {
            // 📌 skip legal check

            val commitedEntity = advertiserProfileImageRepository.commitById(metaEntityId)

            if (commitedEntity != null) {
                AdvertiserProfileImage.of(commitedEntity)
            } else {
                throw NotFoundAdvertiserImageException("adProfileImgSvc- commit")
            }
        }
    }

    fun findByUnifiedCode(unifiedCode: String): BinaryImageDataWithType {
        return transaction {
            val targetEntity = advertiserProfileImageRepository.findByUnifiedCode(unifiedCode)

            if (targetEntity != null) {
                val path = Paths.get(targetEntity.filePath)
                val bytes = Files.readAllBytes(path)
                val type = targetEntity.fileType
                BinaryImageDataWithType.of(
                    bytes = bytes,
                    type = type
                )
            } else {
                throw NotFoundAdvertiserImageException("adProfileImgSvc- findByUnifidedCode")
            }
        }
    }
}