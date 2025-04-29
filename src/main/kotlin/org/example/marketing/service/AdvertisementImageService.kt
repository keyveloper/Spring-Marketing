package org.example.marketing.service

import io.github.oshai.kotlinlogging.KotlinLogging
import org.apache.tika.Tika
import org.example.marketing.domain.board.AdvertisementImage
import org.example.marketing.dto.board.request.MakeNewAdvertisementImageRequest
import org.example.marketing.dto.board.request.SaveAdvertisementImage
import org.example.marketing.dto.board.request.SetAdvertisementThumbnailRequest
import org.example.marketing.dto.board.response.BinaryImageDataWithType
import org.example.marketing.dto.board.response.MakeNewAdvertisementImageResult
import org.example.marketing.exception.IllegalResourceUsageException
import org.example.marketing.exception.InsertLimitationAdImageException
import org.example.marketing.exception.NotFoundAdThumbnailEntityException
import org.example.marketing.repository.board.AdvertisementImageRepository
import org.example.marketing.repository.board.AdvertisementRepository
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*

@Service
class AdvertisementImageService(
    private val advertisementImageRepository: AdvertisementImageRepository,
    private val advertisementRepository: AdvertisementRepository,
) {
    private val rootDirPath = "C:\\Users\\dideh\\Desktop\\Spring\\images\\advertisement"
    private val logger = KotlinLogging.logger {}
    fun save(
        advertiserId: Long,
        meta: MakeNewAdvertisementImageRequest,
        file: MultipartFile
    ): MakeNewAdvertisementImageResult {
        // check owner
        val isLegal = advertisementRepository.checkOwner(advertiserId, meta.advertisementId)

        if (!isLegal) {
            throw IllegalResourceUsageException(
                logics = "advertisementImage-svc: save"
            )
        }

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
        val apiCalUri = "/advertisement/image/$apiUuid"

        logger.info {
            "filename: $fileNameUuid\n" +
                    "filePath: $filePath\n" +
                    "apiUuid: $apiUuid\n" +
                    "apiCalUrl: $apiCalUri\n" +
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
                        apiCallUri = apiCalUri,
                        fileSizeKB = file.size / 1024,
                        fileType = realType
                    )
                )
                MakeNewAdvertisementImageResult.of(savedEntity.id.value, apiCalUri)
            }
        } catch (ex: Exception) {
            runCatching { Files.deleteIfExists(filePath) }
            throw ex
        }
    }

    fun findByIdentifiedUri(uri: String): BinaryImageDataWithType{
        return transaction {
            val targetEntity = advertisementImageRepository.findByApiCalUri(uri)

            // load file
            val path = Paths.get(targetEntity!!.filePath) // not null -> throw ex in repo
            val bytes = Files.readAllBytes(path)
            val type = targetEntity.fileType

            BinaryImageDataWithType.of(
                bytes,
                type
            )
        }
        // find entity
    }

    fun setThumbnailImage(
        advertiserId: Long,
        request: SetAdvertisementThumbnailRequest) {
        return transaction {
            // owner check
            val isLegal = advertisementRepository.checkOwner(advertiserId, request.advertisementId)

            if (!isLegal) {
                throw IllegalResourceUsageException(
                    logics =  "advertisementImage-svc : set Thumbnail Image")
            }

            // if already have
            val oldThumbnailEntity = advertisementImageRepository
                .checkUnexpectedThumbnailsByAdvertisementId(request.advertisementId)
            logger.info {"oldThumbnail: $oldThumbnailEntity"}
            if (oldThumbnailEntity.isNotEmpty()) {
                advertisementImageRepository.withdrawThumbnail(oldThumbnailEntity)
            }

            advertisementImageRepository.setThumbnailById(request.entityId)
        }

    }

    fun findAllMetaDataByAdvertisementId(advertisementId: Long): List<AdvertisementImage> {
        return transaction { advertisementImageRepository.findAllByAdvertisementId(advertisementId)
            .map { AdvertisementImage.of(it) }
        }
    }

    fun findThumbnailUrlByAdvertisementId(advertisementId: Long): String? {
        return transaction {
            val entity = advertisementImageRepository.findThumbnailImageByAdvertisementID(advertisementId)

            entity?.apiCallUri
                ?: throw NotFoundAdThumbnailEntityException(
                    logics = "AdImageService - getThumbnailUrl\n " +
                            "why this advertisementId: $advertisementId doesn't have thumbnail ?? "
                )
        }
    }
}