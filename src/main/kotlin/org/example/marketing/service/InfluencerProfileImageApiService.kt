package org.example.marketing.service

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import io.github.resilience4j.kotlin.circuitbreaker.executeSuspendFunction
import org.example.marketing.dto.user.request.ChangeUserProfileStatusApiRequest
import org.example.marketing.dto.user.request.UploadUserProfileImageApiRequest
import org.example.marketing.dto.user.response.ChangeUserProfileStatusResponseFromServer
import org.example.marketing.dto.user.response.SaveUserProfileImageResult
import org.example.marketing.dto.user.response.UploadUserProfileImageResponseFromServer
import org.example.marketing.enums.ProfileImageType
import org.example.marketing.enums.UserType
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
import java.util.UUID

@Service
class InfluencerProfileImageApiService(
    @Qualifier("imageApiServerClient") private val imageApiServerClient: WebClient,
    private val circuitBreakerRegistry: CircuitBreakerRegistry
) {
    private val logger = KotlinLogging.logger {}
    private val circuitBreaker = circuitBreakerRegistry.circuitBreaker("marketingApiCircuitBreaker")

    suspend fun uploadProfileImageToImageServer(
        userId: UUID,
        userType: UserType,
        profileImageType: ProfileImageType,
        userProfileDraftId: UUID,
        file: MultipartFile
    ): SaveUserProfileImageResult {

        return try {
            circuitBreaker.executeSuspendFunction {
                val apiRequest = UploadUserProfileImageApiRequest.of(
                    userType = userType,
                    userId = userId,
                    profileImageType = profileImageType,
                    userProfileDraftId = userProfileDraftId
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

                val response = imageApiServerClient.post()
                    .uri("/api/user-profile")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData(bodyBuilder.build()))
                    .retrieve()
                    .awaitBody<UploadUserProfileImageResponseFromServer>()

                logger.info { "Received response from image-api-server: msaServiceErrorCode=" +
                        "${response.msaServiceErrorCode}, httpStatus=${response.httpStatus}" }

                when (response.msaServiceErrorCode) {
                    org.example.marketing.enums.MSAServiceErrorCode.OK -> {
                        val result = response.saveUserProfileImageResult
                            ?: throw UploadFailedToImageServerException(
                                logics = "userprofileImageApiServer - uploadToImageServer",
                            )

                        logger.info { "Successfully uploaded image: id=${result.id}, s3Key=${result.s3Key}" }

                        // Convert SaveProfileImageResult to InfluencerProfileImage
                        response.saveUserProfileImageResult
                    }
                    else -> {
                        logger.error { "Upload failed with msaServiceErrorCode=${response.msaServiceErrorCode}" +
                                ", errorMessage=${response.errorMessage}, logics=${response.logics}" }
                        throw UploadFailedToImageServerException(
                            logics = "userprofileImageApiServer - uploadToImageServer",
                        )
                    }
                }
            }
        } catch (ex: Throwable) {
            logger.error { "Failed to upload image to image-api-server: ${ex.message}" }
            throw ex
        }
    }

    suspend fun changeProfileStatusToSave(
        targetEntityId: Long,
        userId: UUID,
        userType: UserType
    ): Long {
        logger.info { "Changing Influencer profile status to save for user: ${userId}, draftId: ${targetEntityId}" }

        return try {
            circuitBreaker.executeSuspendFunction {
                val apiRequest = ChangeUserProfileStatusApiRequest(
                    entityId = targetEntityId,
                    userId = userId,
                    userType = userType
                )

                val response = imageApiServerClient.post()
                    .uri("/api/profile-image-meta/change-status")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(apiRequest)
                    .retrieve()
                    .awaitBody<ChangeUserProfileStatusResponseFromServer>()

                logger.info { "Received response from image-api-server: msaServiceErrorCode=" +
                        "${response.msaServiceErrorCode}, httpStatus=${response.httpStatus}" }

                when (response.msaServiceErrorCode) {
                    org.example.marketing.enums.MSAServiceErrorCode.OK -> {
                        val effectedRow = response.effectedRow
                            ?: throw UploadFailedToImageServerException(
                                logics = "UserProfileImageApiService - changeProfileStatusToSave",
                            )

                        logger.info { "Successfully changed profile status: effectedRow=${effectedRow}" }
                        effectedRow
                    }
                    else -> {
                        logger.error { "Change status failed with msaServiceErrorCode=${response.msaServiceErrorCode}" +
                                ", errorMessage=${response.errorMessage}, logics=${response.logics}" }
                        throw UploadFailedToImageServerException(
                            logics = "UserProfileImageApiService - changeProfileStatusToSave",
                        )
                    }
                }
            }
        } catch (ex: Throwable) {
            logger.error { "Failed to change profile status: ${ex.message}" }
            throw ex
        }
    }

    // Fallback methods
    private fun uploadInfluencerProfileImageToImageServerFallback(
        userId: UUID,
        userType: UserType,
        profileImageType: ProfileImageType,
        userProfileDraftId: UUID,
        file: MultipartFile,
        ex: Throwable
    ): SaveUserProfileImageResult {
        logger.error { "Fallback triggered for uploadInfluencerProfileImageToImageServer: ${ex.message}" }
        throw UploadFailedToImageServerException(
            logics = "InfluencerProfileImageApiService - uploadInfluencerProfileImageToImageServer - Fallback"
        )
    }

    private fun changeInfluencerProfileStatusToSaveFallback(
        targetEntityId: Long,
        userId: UUID,
        userType: UserType,
        ex: Throwable
    ): Long {
        logger.error { "Fallback triggered for changeInfluencerProfileStatusToSave: ${ex.message}" }
        throw UploadFailedToImageServerException(
            logics = "InfluencerProfileImageApiService - changeInfluencerProfileStatusToSave - Fallback"
        )
    }
}