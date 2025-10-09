package org.example.marketing.service

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import io.github.resilience4j.kotlin.circuitbreaker.executeSuspendFunction
import org.example.marketing.domain.board.AdvertisementImage
import org.example.marketing.dto.board.request.SetAdvertisementThumbnailRequest
import org.example.marketing.dto.board.response.BinaryImageDataWithType
import org.example.marketing.dto.user.request.UploadUserProfileImageApiRequest
import org.example.marketing.enums.ProfileImageType
import org.example.marketing.enums.UserType
import org.example.marketing.exception.*
import org.example.marketing.repository.board.AdvertisementImageRepository
import org.example.marketing.repository.board.AdvertisementRepository
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

// service for image-api-server
@Service
class AdvertisementImageApiService(
    private val advertisementImageRepository: AdvertisementImageRepository,
    private val advertisementRepository: AdvertisementRepository,
    private val advertisementDraftService: AdvertisementDraftService,

    @Qualifier("imageApiServerClient") private val marketingApiClient: WebClient,
    private val circuitBreakerRegistry: CircuitBreakerRegistry
) {
    private val logger = KotlinLogging.logger {}
    private val circuitBreaker = circuitBreakerRegistry.circuitBreaker("marketingApiCircuitBreaker")



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

            advertisementImageRepository.setThumbnailById(request.imageId)
        }

    }

    fun findAllMetaDataByAdvertisementId(advertisementId: Long): List<AdvertisementImage> {
        return transaction { advertisementImageRepository.findAllByAdvertisementId(advertisementId)
            .map { AdvertisementImage.of(it) }
        }
    }

    fun findThumbnailUrlByAdvertisementId(advertisementId: Long): String {
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