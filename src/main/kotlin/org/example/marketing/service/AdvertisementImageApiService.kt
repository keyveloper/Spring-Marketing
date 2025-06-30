package org.example.marketing.service

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import io.github.resilience4j.kotlin.circuitbreaker.executeSuspendFunction
import org.example.marketing.domain.board.AdvertisementImageInfo
import org.example.marketing.dto.board.request.ConnectAdvertisementIdRequest
import org.example.marketing.dto.board.request.UploadAdvertisementImageApiRequest
import org.example.marketing.dto.board.response.AdvertisementImageMetadataWithUrl
import org.example.marketing.dto.board.response.AdvertisementImageResponseFromServer
import org.example.marketing.dto.board.response.ConnectAdvertisementResponseFromServer
import org.example.marketing.dto.board.response.ConnectAdvertisementResult
import org.example.marketing.dto.board.response.UploadAdvertisementImageResponseFromServer
import org.example.marketing.exception.MSAErrorException
import org.example.marketing.exception.UploadFailedToImageServerException
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.client.MultipartBodyBuilder
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

// service for image-api-server
@Service
class AdvertisementImageApiService(
    @Qualifier("imageApiServerClient") private val marketingApiClient: WebClient,
    private val advertisementDraftService: AdvertisementDraftService,
    private val circuitBreakerRegistry: CircuitBreakerRegistry
) {
    private val logger = KotlinLogging.logger {}
    private val circuitBreaker = circuitBreakerRegistry.circuitBreaker("marketingApiCircuitBreaker")

    suspend fun uploadToImageServer(
        userId: String,
        advertisementDraftId: Long,
        isThumbnail: Boolean,
        file: MultipartFile
    ): AdvertisementImageInfo {
        logger.info { "Uploading advertisement image to image-api-server for user: " +
                "$userId, isThumbnail: $isThumbnail" }

        return try {
            // validation check
            advertisementDraftService.findById(advertisementDraftId)

            circuitBreaker.executeSuspendFunction {
                val apiRequest = UploadAdvertisementImageApiRequest.of(
                    advertisementDraftId = advertisementDraftId,
                    writerId = userId,
                    isThumbnail = isThumbnail
                )

                // Build multipart body with file and metadata
                val bodyBuilder = MultipartBodyBuilder()

                // Add file part
                bodyBuilder.part("file", file.resource)
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                        "form-data; name=file; filename=${file.originalFilename}")
                    .contentType(MediaType.parseMediaType(file.contentType
                        ?: MediaType.APPLICATION_OCTET_STREAM_VALUE))

                // Add metadata part
                bodyBuilder.part("meta", apiRequest, MediaType.APPLICATION_JSON)

                val response = marketingApiClient.post()
                    .uri("/api/advertisement-images")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData(bodyBuilder.build()))
                    .retrieve()
                    .awaitBody<UploadAdvertisementImageResponseFromServer>()

                logger.info { "Received response from image-api-server: msaServiceErrorCode=" +
                        "${response.msaServiceErrorCode}, httpStatus=${response.httpStatus}" }

                when (response.msaServiceErrorCode) {
                    org.example.marketing.enums.MSAServiceErrorCode.OK -> {
                        val result = response.saveAdvertisementImageResult
                            ?: throw UploadFailedToImageServerException(
                                logics = "AdvertisementImageApiService - uploadToImageServer",
                            )

                        logger.info { "Successfully uploaded advertisement image: id=${result.id}," +
                                " s3Key=${result.s3Key}" }

                        AdvertisementImageInfo(
                            id = result.id,
                            s3Key = result.s3Key,
                            bucketName = result.bucketName,
                            contentType = result.contentType,
                            size = result.size,
                            originalFileName = result.originalFileName,
                        )
                    }
                    else -> {
                        logger.error { "Upload failed with msaServiceErrorCode=${response.msaServiceErrorCode}" +
                                ", errorMessage=${response.errorMessage}, logics=${response.logics}" }
                        throw UploadFailedToImageServerException(
                            logics = "AdvertisementImageApiService - uploadToImageServer",
                        )
                    }
                }
            }
        } catch (ex: Throwable) {
            logger.error { "Failed to upload advertisement image to image-api-server: ${ex.message}" }
            throw ex
        }
    }

    suspend fun fetchImageByAdvertisementId(adId: Long): List<AdvertisementImageMetadataWithUrl> {
        logger.info {"fetch advertisementImageMetadataWithUrl: adId: ${adId}"}

        return try {
            circuitBreaker.executeSuspendFunction {
                val response = marketingApiClient.get()
                    .uri("/api/advertisement-images/{adId}", adId)
                    .retrieve()
                    .awaitBody<AdvertisementImageResponseFromServer>()


                // ⚒️ Debugging logs
                logger.info { "Received response from image-api-server: msaServiceErrorCode=" +
                        "${response.msaServiceErrorCode}, httpStatus=${response.httpStatus}, " +
                        "imageCount=${response.result.size}" }

                response.result.forEachIndexed { index, metadata ->
                    logger.info { "Image[$index]: bucketName=${metadata.bucketName}, s3Key=${metadata.s3Key}, " +
                            "contentType=${metadata.contentType}, size=${metadata.size}, " +
                            "originalFileName=${metadata.originalFileName}, isThumbnail=${metadata.isThumbnail}" }
                }

                logger.info { "Successfully fetched images for advertisement: $adId" }
                response.result
            }
        } catch (ex: Throwable) {
            logger.error { "Failed to fetch advertisement images for adId=$adId: ${ex.message}" }
            throw ex
        }

    }

    suspend fun fetchImageByAdvertisementIdFallback(
        adId: Long,
        throwable: Throwable
    ): List<AdvertisementImageMetadataWithUrl> {
        logger.error { "Circuit breaker fallback triggered for fetchImageByAdvertisementId: ${throwable.message}" }
        logger.error { "Failed request details - adId: $adId" }
        return emptyList()
    }

    suspend fun uploadToImageServerFallback(
        userId: Long,
        isThumbnail: Boolean,
        file: MultipartFile,
        throwable: Throwable
    ): AdvertisementImageInfo {
        logger.error { "Circuit breaker fallback triggered for uploadToImageServer: ${throwable.message}" }
        logger.error { "Failed request details - userId: $userId, isThumbnail: $isThumbnail, fileName: ${file.originalFilename}" }
        throw UploadFailedToImageServerException(
            logics = "AdvertisementImageApiService - uploadToImageServer fallback",
            message = "Failed to upload advertisement image to image-api-server: ${throwable.message}"
        )
    }

    suspend fun connectAdvertisementToImageServer(
        draftId: Long,
        advertisementId: Long
    ): ConnectAdvertisementResult {
        logger.info { "Connecting advertisement images to advertisement: draftId=$draftId, advertisementId=$advertisementId" }

        return try {
            circuitBreaker.executeSuspendFunction {
                val request = ConnectAdvertisementIdRequest(
                    draftId = draftId,
                    advertisementId = advertisementId
                )

                val response = marketingApiClient.post()
                    .uri("/api/advertisement-images/connect/advertisement")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(request)
                    .retrieve()
                    .awaitBody<ConnectAdvertisementResponseFromServer>()

                logger.info { "Received response from image-api-server: msaServiceErrorCode=" +
                        "${response.msaServiceErrorCode}, httpStatus=${response.httpStatus}" }

                when (response.msaServiceErrorCode) {
                    org.example.marketing.enums.MSAServiceErrorCode.OK -> {
                        val result = response.result

                        logger.info { "Successfully connected advertisement images: " +
                                "updatedRow=${result.updatedRow}, " +
                                "connectedKeys=${result.connectedS3BucketKeys.joinToString()}" }

                        result
                    }
                    else -> {
                        logger.error { "Connection failed with msaServiceErrorCode=${response.msaServiceErrorCode}" +
                                ", errorMessage=${response.errorMessage}, logics=${response.logics}" }
                        throw MSAErrorException(
                            logics = "AdvertisementImageApiService - connectAdvertisementToImageServer",
                            message = response.errorMessage ?: "Failed to connect advertisement images"
                        )
                    }
                }
            }
        } catch (ex: Throwable) {
            logger.error { "Failed to connect advertisement to image-api-server: ${ex.message}" }
            throw ex
        }
    }
}