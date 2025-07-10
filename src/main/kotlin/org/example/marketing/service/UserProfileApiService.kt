package org.example.marketing.service

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import io.github.resilience4j.kotlin.circuitbreaker.executeSuspendFunction
import org.example.marketing.dto.user.request.UploadAdvertiserProfileImageApiRequest
import org.example.marketing.dto.user.request.UploadInfluencerProfileImageApiRequest
import org.example.marketing.dto.user.response.SaveAdvertisementProfileImageResult
import org.example.marketing.dto.user.response.SaveInfluencerProfileImageResult
import org.example.marketing.dto.user.response.UploadInfluencerProfileImageResponseFromServer
import org.example.marketing.dto.user.response.UploadProfileImageResponseFromServer
import org.example.marketing.enums.MSAServiceErrorCode
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

@Service
class UserProfileApiService(
    @Qualifier("userProfileApiClient") private val userProfileApiClient: WebClient,
    private val circuitBreakerRegistry: CircuitBreakerRegistry
) {
    private val logger = KotlinLogging.logger {}
    private val circuitBreaker = circuitBreakerRegistry.circuitBreaker("userProfileApiCircuitBreaker")

    suspend fun uploadAdvertiserProfileImageToApiServer(
        userId: String,
        userType: String,
        advertiserProfileDraftId: String,
        file: MultipartFile
    ): SaveAdvertisementProfileImageResult {
        logger.info { "Uploading profile image to user-profile-api-server for user: $userId" }

        return try {
            circuitBreaker.executeSuspendFunction {
                val metaRequest = UploadAdvertiserProfileImageApiRequest(
                    userId = userId,
                    userType = userType,
                    advertiserProfileDraftId = advertiserProfileDraftId,
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
                bodyBuilder.part("meta", metaRequest, MediaType.APPLICATION_JSON)

                val response = userProfileApiClient.post()
                    .uri("/api/advertiser-profile-images")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData(bodyBuilder.build()))
                    .retrieve()
                    .awaitBody<UploadProfileImageResponseFromServer>()

                logger.info { "Received response from user-profile-api-server: msaServiceErrorCode=" +
                        "${response.msaServiceErrorCode}, httpStatus=${response.httpStatus}" }

                when (response.msaServiceErrorCode) {
                    MSAServiceErrorCode.OK -> {
                        val result = response.result
                            ?: throw UploadFailedToImageServerException(
                                logics = "UserProfileApiService - uploadProfileImage",
                            )

                        logger.info { "Successfully uploaded profile image: id=${result.id}," +
                                " s3Key=${result.s3Key}" }

                        result
                    }
                    else -> {
                        logger.error { "Upload failed with msaServiceErrorCode=${response.msaServiceErrorCode}" +
                                ", errorMessage=${response.errorMessage}, logics=${response.logics}" }
                        throw UploadFailedToImageServerException(
                            logics = "UserProfileApiService - uploadProfileImage",
                        )
                    }
                }
            }
        } catch (ex: Throwable) {
            logger.error { "Failed to upload profile image to user-profile-api-server: ${ex.message}" }
            throw ex
        }
    }

    suspend fun uploadInfluencerProfileImageToApiServer(
        userId: String,
        userType: String,
        influencerProfileDraftId: String,
        file: MultipartFile
    ): SaveInfluencerProfileImageResult {
        logger.info { "Uploading influencer profile image to user-profile-api-server for user: $userId" }

        return try {
            circuitBreaker.executeSuspendFunction {
                val metaRequest = UploadInfluencerProfileImageApiRequest(
                    userId = userId,
                    userType = userType,
                    influencerProfileDraftId = influencerProfileDraftId,
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
                bodyBuilder.part("meta", metaRequest, MediaType.APPLICATION_JSON)

                val response = userProfileApiClient.post()
                    .uri("/api/influencer-profile-images")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData(bodyBuilder.build()))
                    .retrieve()
                    .awaitBody<UploadInfluencerProfileImageResponseFromServer>()

                logger.info { "Received response from user-profile-api-server: msaServiceErrorCode=" +
                        "${response.msaServiceErrorCode}, httpStatus=${response.httpStatus}" }

                when (response.msaServiceErrorCode) {
                    MSAServiceErrorCode.OK -> {
                        val result = response.saveInfluencerProfileImageResult
                            ?: throw UploadFailedToImageServerException(
                                logics = "UserProfileApiService - uploadInfluencerProfileImage",
                            )

                        logger.info { "Successfully uploaded influencer profile image: id=${result.id}," +
                                " s3Key=${result.s3Key}" }

                        result
                    }
                    else -> {
                        logger.error { "Upload failed with msaServiceErrorCode=${response.msaServiceErrorCode}" +
                                ", errorMessage=${response.errorMessage}, logics=${response.logics}" }
                        throw UploadFailedToImageServerException(
                            logics = "UserProfileApiService - uploadInfluencerProfileImage",
                        )
                    }
                }
            }
        } catch (ex: Throwable) {
            logger.error { "Failed to upload influencer profile image to user-profile-api-server: ${ex.message}" }
            throw ex
        }
    }

    // ==================== Fallback Methods ====================

    suspend fun uploadAdvertiserProfileImageFallback(
        userId: String,
        file: MultipartFile,
        throwable: Throwable
    ): SaveAdvertisementProfileImageResult {
        logger.error { "Circuit breaker fallback triggered for uploadAdvertiserProfileImage: ${throwable.message}" }
        logger.error { "Failed request details - userId: $userId, fileName: ${file.originalFilename}" }
        throw UploadFailedToImageServerException(
            logics = "UserProfileApiService - uploadAdvertiserProfileImage fallback",
            message = "Failed to upload advertiser profile image to user-profile-api-server: ${throwable.message}"
        )
    }

    suspend fun uploadInfluencerProfileImageFallback(
        userId: String,
        file: MultipartFile,
        throwable: Throwable
    ): SaveInfluencerProfileImageResult {
        logger.error { "Circuit breaker fallback triggered for uploadInfluencerProfileImage: ${throwable.message}" }
        logger.error { "Failed request details - userId: $userId, fileName: ${file.originalFilename}" }
        throw UploadFailedToImageServerException(
            logics = "UserProfileApiService - uploadInfluencerProfileImage fallback",
            message = "Failed to upload influencer profile image to user-profile-api-server: ${throwable.message}"
        )
    }
}
