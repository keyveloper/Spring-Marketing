package org.example.marketing.service

import io.github.oshai.kotlinlogging.KotlinLogging
import org.apache.tika.Tika
import org.example.marketing.dto.board.request.MakeNewAdvertisementImageRequest
import org.example.marketing.dto.board.request.SaveAdvertisementImage
import org.example.marketing.dto.board.request.SetAdvertisementThumbnailRequest
import org.example.marketing.dto.board.response.BinaryImageDataWithType
import org.example.marketing.dto.board.response.MakeNewAdvertisementImageResult
import org.example.marketing.exception.InsertLimitationAdImageException
import org.example.marketing.exception.NotFoundAdThumbnailEntityException
import org.example.marketing.repository.board.AdvertisementImageRepository
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*

@Service
class AdvertisementImageService(
    private val advertisementImageRepository: AdvertisementImageRepository
) {
    private val rootDirPath = "C:\\Users\\dideh\\Desktop\\Spring\\images\\advertisement"
    private val logger = KotlinLogging.logger {}
    fun save(
        meta: MakeNewAdvertisementImageRequest,
        file: MultipartFile
    ): MakeNewAdvertisementImageResult {
        // check count first
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
        val fileNameUuid = "${UUID.randomUUID()}.$extension"
        val filePath = dir.resolve(fileNameUuid)
        val apiUuid = UUID.randomUUID().toString()
        val apiCalUrl = "/advertisement/image/$apiUuid"

        logger.info {
            "filename: $fileNameUuid\n" +
                    "filePath: $filePath\n" +
                    "apiUuid: $apiUuid\n" +
                    "apiCalUrl: $apiCalUrl\n" +
                    "extension : $extension"
        }

        file.inputStream.use {
            Files.copy(it, filePath, StandardCopyOption.REPLACE_EXISTING)
        }

        try {
            return transaction {
                val count = advertisementImageRepository.findAllByAdvertisementId(meta.advertisementId)
                    .size
                if (count >= 5) {
                    throw InsertLimitationAdImageException()
                }

                val savedEntity = advertisementImageRepository.save(
                    SaveAdvertisementImage.of(
                        meta = meta,
                        convertedFileName = fileNameUuid,
                        filePath = filePath.toString(),
                        apiCallUrl = apiCalUrl,
                        fileSizeKB = file.size / 1024,
                        fileType = realType
                    )
                )
                MakeNewAdvertisementImageResult.of(savedEntity.id.value, apiCalUrl)
            }
        } catch (ex: Exception) {
            runCatching { Files.deleteIfExists(filePath) }
            throw ex
        }
    }

    fun findByIdentifiedUrl(url: String): BinaryImageDataWithType{
        // find entity
        val uuidFromUrl = url.substringAfterLast("/")
        val targetEntity = advertisementImageRepository.findByApiCalUrl(uuidFromUrl)

        // load file
        val path = Paths.get(targetEntity!!.filePath) // not null -> throw ex in repo
        val bytes = Files.readAllBytes(path)
        val type = targetEntity.fileType

        return BinaryImageDataWithType.of(
            bytes,
            type
        )
    }

    fun setThumbnailImage(request: SetAdvertisementThumbnailRequest) {
        return transaction {
            // if already have
            val oldThumbnailEntity = advertisementImageRepository
                .checkUnexpectedThumbnailsByAdvertisementId(request.advertisementLId)
            logger.info {"oldThumbnail: $oldThumbnailEntity"}
            if (oldThumbnailEntity.isNotEmpty()) {
                advertisementImageRepository.withdrawThumbnail(oldThumbnailEntity)
            }

            advertisementImageRepository.setThumbnailById(request.entityId)
        }

    }

    fun getThumbnailUrlByAdvertisementId(targetAdvertisementId: Long): String? {
        return transaction {
            val entity = advertisementImageRepository.findThumbnailImageByAdvertisementID(targetAdvertisementId)

            entity?.apiCallUrl
                ?: throw NotFoundAdThumbnailEntityException(
                    logics = "AdImageService - getThumbnailUrl\n " +
                            "why this advertisementId: $targetAdvertisementId doesn't have thumbnail ?? "
                )
        }
    }
}