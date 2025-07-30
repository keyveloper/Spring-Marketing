package org.example.marketing.service

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import io.github.resilience4j.kotlin.circuitbreaker.executeSuspendFunction
import org.example.marketing.domain.user.InfluencerProfile
import org.example.marketing.dto.user.request.UpdateInfluencerProfileInfoApiRequest
import org.example.marketing.dto.user.request.UploadInfluencerProfileInfoApiRequest
import org.example.marketing.dto.user.response.*
import org.example.marketing.enums.MSAServiceErrorCode
import org.example.marketing.enums.UserType
import org.example.marketing.exception.UploadFailedToImageServerException
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import java.util.*

@Service
class InfluencerProfileApiService(
    @Qualifier("userProfileApiClient") private val userProfileApiClient: WebClient,
    private val influencerProfileImageApiService: InfluencerProfileImageApiService,
    private val circuitBreakerRegistry: CircuitBreakerRegistry
) {
    private val logger = KotlinLogging.logger {}
    private val circuitBreaker = circuitBreakerRegistry.circuitBreaker("marketingApiCircuitBreaker")

    suspend fun uploadInfluencerProfileInfoToApiServer(
        influencerId: UUID,
        userType: UserType,
        userProfileDraftId: UUID,
        introduction: String?,
        job: String?,
    ): SaveInfluencerProfileInfoResult {
        logger.info { "Uploading influencer profile info for user: $influencerId, draftId: $userProfileDraftId" }

        return newSuspendedTransaction {
            try {
                // Step 1: Upload profile info to user-profile-api
                val result = circuitBreaker.executeSuspendFunction {
                    val apiRequest = UploadInfluencerProfileInfoApiRequest(
                        influencerId = influencerId,
                        userProfileDraftId = userProfileDraftId,
                        introduction = introduction,
                        job = job
                    )

                    val response = userProfileApiClient.post()
                        .uri("/api/v1/influencer-profiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(apiRequest)
                        .retrieve()
                        .awaitBody<SaveInfluencerProfileInfoResponseFromServer>()

                    logger.info { "Received response from user-profile-api: msaServiceErrorCode=" +
                            "${response.msaServiceErrorCode}, httpStatus=${response.httpStatus}" }

                    when (response.msaServiceErrorCode) {
                        MSAServiceErrorCode.OK -> {
                            val result = response.saveInfluencerProfileInfoResult
                                ?: throw UploadFailedToImageServerException(
                                    logics = "InfluencerProfileApiService - uploadInfluencerProfileInfoToApiServer",
                                )

                            logger.info { "Successfully uploaded influencer profile info: savedId=${result.savedId}" }
                            result
                        }
                        else -> {
                            logger.error { "Upload failed with msaServiceErrorCode=${response.msaServiceErrorCode}" +
                                    ", errorMessage=${response.errorMessage}, logics=${response.logics}" }
                            throw UploadFailedToImageServerException(
                                logics = "InfluencerProfileApiService - uploadInfluencerProfileInfoToApiServer",
                            )
                        }
                    }
                }

                // Step 2: Change profile image status to save
                influencerProfileImageApiService.changeProfileStatusToSave(
                    targetEntityId = result.savedId,
                    userId = influencerId,
                    userType = userType
                )
                logger.info { "Successfully changed profile image status for savedId: ${result.savedId}" }

                result
            } catch (ex: Throwable) {
                logger.error { "Failed to upload influencer profile info: ${ex.message}" }
                throw ex
            }
        }
    }

    suspend fun getInfluencerProfileInfoById(influencerId: String): GetInfluencerProfileInfoWithImages? {
        logger.info { "Getting influencer profile info for influencerId: $influencerId" }

        return try {
            circuitBreaker.executeSuspendFunction {
                val response = userProfileApiClient.get()
                    .uri("/api/v1/influencer-profiles/$influencerId")
                    .retrieve()
                    .awaitBody<GetInfluencerProfileInfoResponseFromServer>()

                logger.info { "Received response from user-profile-api: msaServiceErrorCode=" +
                        "${response.msaServiceErrorCode}, httpStatus=${response.httpStatus}" }

                when (response.msaServiceErrorCode) {
                    MSAServiceErrorCode.OK -> {
                        val profileInfoResult = response.getInfluencerProfileInfoResult
                        if (profileInfoResult != null) {
                            val profileImages = influencerProfileImageApiService.getUserProfileImagesByUserId(
                                UUID.fromString(influencerId)
                            )
                            GetInfluencerProfileInfoWithImages.of(profileInfoResult, profileImages)
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
            logger.error { "Failed to get influencer profile info: ${ex.message}" }
            null
        }
    }

    suspend fun updateInfluencerProfileInfoById(influencerId: String, domain: InfluencerProfile): UpdateInfluencerProfileInfoResult {
        logger.info { "Updating influencer profile info for influencerId: $influencerId" }

        return circuitBreaker.executeSuspendFunction {
            val apiRequest = UpdateInfluencerProfileInfoApiRequest(
                userProfileDraftId = domain.userProfileDraftId,
                influencerId = domain.influencerId,
                introduction = domain.introduction,
                job = domain.job
            )

            val response = userProfileApiClient.put()
                .uri("/api/v1/influencer-profiles/$influencerId")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(apiRequest)
                .retrieve()
                .awaitBody<UpdateInfluencerProfileInfoResponseFromServer>()

            logger.info { "Received response from user-profile-api: msaServiceErrorCode=" +
                    "${response.msaServiceErrorCode}, httpStatus=${response.httpStatus}" }

            when (response.msaServiceErrorCode) {
                MSAServiceErrorCode.OK -> {
                    val result = response.updateInfluencerProfileInfoResult
                        ?: throw RuntimeException("Update result is null")
                    logger.info { "Successfully updated influencer profile info: updatedCount=${result.updatedCount}" }
                    result
                }
                else -> {
                    logger.error { "Update failed with msaServiceErrorCode=${response.msaServiceErrorCode}" +
                            ", errorMessage=${response.errorMessage}" }
                    throw RuntimeException("Failed to update influencer profile: ${response.errorMessage}")
                }
            }
        }
    }

    suspend fun deleteInfluencerProfileInfoById(influencerId: String): DeleteInfluencerProfileInfoResult {
        logger.info { "Deleting influencer profile info for influencerId: $influencerId" }

        return circuitBreaker.executeSuspendFunction {
            val response = userProfileApiClient.delete()
                .uri("/api/v1/influencer-profiles/$influencerId")
                .retrieve()
                .awaitBody<DeleteInfluencerProfileInfoResponseFromServer>()

            logger.info { "Received response from user-profile-api: msaServiceErrorCode=" +
                    "${response.msaServiceErrorCode}, httpStatus=${response.httpStatus}" }

            when (response.msaServiceErrorCode) {
                MSAServiceErrorCode.OK -> {
                    val result = response.deleteInfluencerProfileInfoResult
                        ?: throw RuntimeException("Delete result is null")
                    logger.info { "Successfully deleted influencer profile info: deletedCount=${result.deletedCount}" }
                    result
                }
                else -> {
                    logger.error { "Delete failed with msaServiceErrorCode=${response.msaServiceErrorCode}" +
                            ", errorMessage=${response.errorMessage}" }
                    throw RuntimeException("Failed to delete influencer profile: ${response.errorMessage}")
                }
            }
        }
    }

    // Fallback methods
    private fun getInfluencerProfileInfoByIdFallback(influencerId: String, ex: Throwable): GetInfluencerProfileInfoResult? {
        logger.error { "Fallback triggered for getInfluencerProfileInfoById: ${ex.message}" }
        return null
    }

    private fun updateInfluencerProfileInfoByIdFallback(influencerId: String, domain: InfluencerProfile, ex: Throwable): UpdateInfluencerProfileInfoResult {
        logger.error { "Fallback triggered for updateInfluencerProfileInfoById: ${ex.message}" }
        return UpdateInfluencerProfileInfoResult(updatedCount = -1)
    }

    private fun deleteInfluencerProfileInfoByIdFallback(influencerId: String, ex: Throwable): DeleteInfluencerProfileInfoResult {
        logger.error { "Fallback triggered for deleteInfluencerProfileInfoById: ${ex.message}" }
        return DeleteInfluencerProfileInfoResult(deletedCount = -1)
    }
}
