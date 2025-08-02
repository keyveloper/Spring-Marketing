package org.example.marketing.service

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import io.github.resilience4j.kotlin.circuitbreaker.executeSuspendFunction
import org.example.marketing.domain.user.AdvertiserProfile
import org.example.marketing.dto.user.request.UpdateAdvertiserProfileInfoApiRequest
import org.example.marketing.dto.user.request.UploadAdvertiserProfileInfoApiRequest
import org.example.marketing.dto.user.response.*
import org.example.marketing.enums.MSAServiceErrorCode
import org.example.marketing.enums.UserType
import org.example.marketing.exception.UploadFailedToImageServerException
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import java.util.*

@Service
class AdvertiserProfileApiService(
    @Qualifier("userProfileApiClient") private val userProfileApiClient: WebClient,
    private val advertiserProfileImageApiService: AdvertiserProfileImageApiService,
    private val circuitBreakerRegistry: CircuitBreakerRegistry
) {
    private val logger = KotlinLogging.logger {}
    private val circuitBreaker = circuitBreakerRegistry.circuitBreaker("marketingApiCircuitBreaker")

    suspend fun uploadAdvertiserProfileInfoToApiServer(
        advertiserId: UUID,
        advertiserName: String,
        userProfileDraftId: UUID,
        serviceInfo: String,
        locationBrief: String,
        introduction: String?,
        userType: UserType
    ): SaveAdvertiserProfileInfoApiResult {
        logger.info { "Uploading advertiser profile info for user: $advertiserId, draftId: $userProfileDraftId" }

        return newSuspendedTransaction {
            try {
                // Step 1: Upload profile info to user-profile-api
                circuitBreaker.executeSuspendFunction {
                    val apiRequest = UploadAdvertiserProfileInfoApiRequest(
                        advertiserId = advertiserId,
                        advertiserName = advertiserName,
                        userProfileDraftId = userProfileDraftId,
                        serviceInfo = serviceInfo,
                        locationBrief = locationBrief,
                        introduction = introduction
                    )

                    val response = userProfileApiClient.post()
                        .uri("/api/v1/advertiser-profiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(apiRequest)
                        .retrieve()
                        .awaitBody<SaveAdvertiserProfileInfoResponseFromServer>()

                    logger.info { "Received response from user-profile-api: msaServiceErrorCode=" +
                            "${response.msaServiceErrorCode}, httpStatus=${response.httpStatus}" }

                    when (response.msaServiceErrorCode) {
                        MSAServiceErrorCode.OK -> {
                            val result = response.saveAdvertiserProfileInfoResult
                                ?: throw UploadFailedToImageServerException(
                                    logics = "AdvertiserProfileService - uploadAdvertiserProfileInfoToApiServer",
                                )

                            logger.info {
                                "Successfully uploaded advertiser profile info: savedId=${result.savedEntityId}"
                            }
                        }
                        else -> {
                            logger.error { "Upload failed with msaServiceErrorCode=${response.msaServiceErrorCode}" +
                                    ", errorMessage=${response.errorMessage}, logics=${response.logics}" }
                            throw UploadFailedToImageServerException(
                                logics = "AdvertiserProfileService - uploadAdvertiserProfileInfoToApiServer",
                            )
                        }
                    }

                    val savedEntityId = response.saveAdvertiserProfileInfoResult.savedEntityId

                    val changeResult = advertiserProfileImageApiService.changeProfileStatusToSave(
                            targetEntityId = savedEntityId,
                            userId = advertiserId,
                            userType = userType
                    )
                    logger.info { "Successfully changed profile image status for savedEntityId: ${savedEntityId}" }

                    // Step 2: Change profile image status to save
                    SaveAdvertiserProfileInfoApiResult.of(
                        response.saveAdvertiserProfileInfoResult,
                        changeResult
                    )
                }
            } catch (ex: Throwable) {
                logger.error { "Failed to upload advertiser profile info: ${ex.message}" }
                throw ex
            }
        }
    }

    suspend fun getAdvertiserProfileInfoById(advertiserId: String): GetAdvertiserProfileInfoWithImages? {
        logger.info { "Getting advertiser profile info for advertiserId: $advertiserId" }

        return try {
            circuitBreaker.executeSuspendFunction {
                val response = userProfileApiClient.get()
                    .uri("/api/v1/advertiser-profiles/$advertiserId")
                    .retrieve()
                    .awaitBody<GetAdvertiserProfileInfoResponseFromServer>()

                logger.info { "Received response from user-profile-api: msaServiceErrorCode=" +
                        "${response.msaServiceErrorCode}, httpStatus=${response.httpStatus}" }

                when (response.msaServiceErrorCode) {
                    MSAServiceErrorCode.OK -> {
                        val profileInfoResult = response.getAdvertiserProfileInfoResult
                        if (profileInfoResult != null) {
                            val profileImages = advertiserProfileImageApiService.getUserProfileImagesByUserId(
                                UUID.fromString(advertiserId)
                            )
                            GetAdvertiserProfileInfoWithImages.of(profileInfoResult, profileImages)
                        } else {
                            null
                        }
                    }
                    else -> {
                        logger.error { "Get failed with msaServiceErrorCode=${response.msaServiceErrorCode}" +
                                ", errorMessage=${response.errorMessage}" }
                        null
                    }
                }
            }
        } catch (ex: Throwable) {
            logger.error { "Failed to get advertiser profile info: ${ex.message}" }
            null
        }
    }

    suspend fun updateAdvertiserProfileInfoById(advertiserId: String, domain: AdvertiserProfile): UpdateAdvertiserProfileInfoResult {
        logger.info { "Updating advertiser profile info for advertiserId: $advertiserId" }

        return circuitBreaker.executeSuspendFunction {
            val apiRequest = UpdateAdvertiserProfileInfoApiRequest(
                advertiserId = domain.advertiserId,
                userProfileDraftId = domain.userProfileDraftId,
                serviceInfo = domain.serviceInfo,
                locationBrief = domain.locationBrief,
                introduction = domain.introduction
            )

            val response = userProfileApiClient.put()
                .uri("/api/v1/advertiser-profiles/$advertiserId")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(apiRequest)
                .retrieve()
                .awaitBody<UpdateAdvertiserProfileInfoResponseFromServer>()

            logger.info { "Received response from user-profile-api: msaServiceErrorCode=" +
                    "${response.msaServiceErrorCode}, httpStatus=${response.httpStatus}" }

            when (response.msaServiceErrorCode) {
                MSAServiceErrorCode.OK -> {
                    val result = response.updateAdvertiserProfileInfoResult
                        ?: throw RuntimeException("Update result is null")
                    logger.info { "Successfully updated advertiser profile info: updatedCount=${result.updatedCount}" }
                    result
                }
                else -> {
                    logger.error { "Update failed with msaServiceErrorCode=${response.msaServiceErrorCode}" +
                            ", errorMessage=${response.errorMessage}" }
                    throw RuntimeException("Failed to update advertiser profile: ${response.errorMessage}")
                }
            }
        }
    }

    suspend fun deleteAdvertiserProfileInfoById(advertiserId: String): DeleteAdvertiserProfileInfoResult {
        logger.info { "Deleting advertiser profile info for advertiserId: $advertiserId" }

        return circuitBreaker.executeSuspendFunction {
            val response = userProfileApiClient.delete()
                .uri("/api/v1/advertiser-profiles/$advertiserId")
                .retrieve()
                .awaitBody<DeleteAdvertiserProfileInfoResponseFromServer>()

            logger.info { "Received response from user-profile-api: msaServiceErrorCode=" +
                    "${response.msaServiceErrorCode}, httpStatus=${response.httpStatus}" }

            when (response.msaServiceErrorCode) {
                MSAServiceErrorCode.OK -> {
                    val result = response.deleteAdvertiserProfileInfoResult
                        ?: throw RuntimeException("Delete result is null")
                    logger.info { "Successfully deleted advertiser profile info: deletedCount=${result.deletedCount}" }
                    result
                }
                else -> {
                    logger.error { "Delete failed with msaServiceErrorCode=${response.msaServiceErrorCode}" +
                            ", errorMessage=${response.errorMessage}" }
                    throw RuntimeException("Failed to delete advertiser profile: ${response.errorMessage}")
                }
            }
        }
    }

    // Fallback methods
    private fun getAdvertiserProfileInfoByIdFallback(
        advertiserId: String,
        ex: Throwable
    ): GetAdvertiserProfileInfoResult? {
        logger.error { "Fallback triggered for getAdvertiserProfileInfoById: ${ex.message}" }
        return null
    }

    private fun updateAdvertiserProfileInfoByIdFallback(
        advertiserId: String,
        domain: AdvertiserProfile,
        ex: Throwable
    ): UpdateAdvertiserProfileInfoResult {
        logger.error { "Fallback triggered for updateAdvertiserProfileInfoById: ${ex.message}" }
        return UpdateAdvertiserProfileInfoResult(updatedCount = -1)
    }

    private fun deleteAdvertiserProfileInfoByIdFallback(
        advertiserId: String,
        ex: Throwable
    ): DeleteAdvertiserProfileInfoResult {
        logger.error { "Fallback triggered for deleteAdvertiserProfileInfoById: ${ex.message}" }
        return DeleteAdvertiserProfileInfoResult(deletedCount = -1)
    }
}
