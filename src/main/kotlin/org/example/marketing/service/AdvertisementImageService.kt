package org.example.marketing.service

import io.github.oshai.kotlinlogging.KotlinLogging
import org.apache.tika.Tika
import org.example.marketing.config.CustomDateTimeFormatter
import org.example.marketing.domain.board.AdvertisementImage
import org.example.marketing.dto.board.request.MakeNewAdvertisementImageRequest
import org.example.marketing.dto.board.request.SaveAdvertisementImage
import org.example.marketing.dto.board.request.SetAdvertisementThumbnailRequest
import org.example.marketing.dto.board.response.BinaryImageDataWithType
import org.example.marketing.exception.*
import org.example.marketing.repository.board.AdvertisementImageRepository
import org.example.marketing.repository.board.AdvertisementRepository
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*

@Service
class AdvertisementImageService(
    private val advertisementImageRepository: AdvertisementImageRepository,
    private val advertisementRepository: AdvertisementRepository,
    private val advertisementDraftService: AdvertisementDraftService
) {
    private val rootDirPath = "C:\\Users\\dideh\\Desktop\\Spring\\images\\advertisement"
    private val logger = KotlinLogging.logger {}
    fun save(
        advertiserId: Long,
        meta: MakeNewAdvertisementImageRequest,
        file: MultipartFile
    ): AdvertisementImage {

        // extract meta data
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

        // 🙄 orphan file ...
        file.inputStream.use {
            Files.copy(it, filePath, StandardCopyOption.REPLACE_EXISTING)
        }

        try {
            return transaction {
                // draft validation check
                val draft = advertisementDraftService.findById(meta.draftId)

                // limitation check
                val count = advertisementImageRepository.findAllByAdvertisementId(meta.draftId)
                    .size
                if (count >= 5) {
                    throw InsertLimitationAdImageException()
                }

                // expired check
                val apiCallAt = System.currentTimeMillis() / 1000
                if (draft.expiredAt < apiCallAt) {
                    throw ExpiredDraftException(
                        logics = "advertisementDraft svc - save - draft expried checking",
                        expiredAt = CustomDateTimeFormatter.epochToString(draft.expiredAt),
                        apiCallAt = CustomDateTimeFormatter.epochToString(apiCallAt)
                    )
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
                AdvertisementImage.of(savedEntity)
            }
        } catch (ex: Exception) {
            runCatching { Files.deleteIfExists(filePath) }
            throw ex
        }
    }

    fun findByIdentifiedUri(uri: String): BinaryImageDataWithType{
        return transaction {
            val recoveredOriginalUri = StringBuilder().append("/advertisement/image/").append(uri).toString()
            logger.info { "recoveredOriginalUri: $recoveredOriginalUri" }
            val targetEntity = advertisementImageRepository.findByApiCalUri(recoveredOriginalUri)

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

    fun deleteById(advertiserId: Long, metaId: Long) {
        return transaction {
            // find with owner info ->
            val targetEntity = advertisementImageRepository.findById(metaId)
            val savedFilePath = targetEntity!!.filePath
            val file = File(savedFilePath)

            if (file.exists()) {
                file.delete()
            } else {
                throw NotExistFileException(
                    logics = "advertisementImage-svc : delete file not found",
                    filePath = savedFilePath
                )
            }
            advertisementImageRepository.deleteByIdWithOwnerChecking(advertiserId, metaId)
            //  👉 fix logic - owner check !!

        }
    }
}