package org.example.marketing.service

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import io.github.resilience4j.kotlin.circuitbreaker.executeSuspendFunction
import org.example.marketing.dto.user.request.UploadAdvertiserProfileInfoApiRequest
import org.example.marketing.dto.user.request.UploadInfluencerProfileInfoApiRequest
import org.example.marketing.dto.user.response.SaveAdvertiserProfileInfoApiResult
import org.example.marketing.dto.user.response.SaveAdvertiserProfileInfoResponseFromServer
import org.example.marketing.dto.user.response.SaveInfluencerProfileInfoResponseFromServer
import org.example.marketing.dto.user.response.SaveInfluencerProfileInfoResult
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
class UserProfileApiService(
    @Qualifier("userProfileApiClient") private val userProfileApiClient: WebClient,
    private val userProfileImageApiService: UserProfileImageApiService,
    private val circuitBreakerRegistry: CircuitBreakerRegistry
) {
    private val logger = KotlinLogging.logger {}
    private val circuitBreaker = circuitBreakerRegistry.circuitBreaker("marketingApiCircuitBreaker")

    suspend fun uploadAdvertiserProfileInfoToApiServer(
        advertiserId: UUID,
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
                                    logics = "UserProfileApiService - uploadAdvertiserProfileInfoToApiServer",
                                )

                            logger.info {
                                "Successfully uploaded advertiser profile info: savedId=${result.savedEntityId}"
                            }
                        }
                        else -> {
                            logger.error { "Upload failed with msaServiceErrorCode=${response.msaServiceErrorCode}" +
                                    ", errorMessage=${response.errorMessage}, logics=${response.logics}" }
                            throw UploadFailedToImageServerException(
                                logics = "UserProfileApiService - uploadAdvertiserProfileInfoToApiServer",
                            )
                        }
                    }

                    val savedEntityId = response.saveAdvertiserProfileInfoResult.savedEntityId

                    val changeResult = userProfileImageApiService.changeProfileStatusToSave(
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
                                    logics = "UserProfileApiService - uploadInfluencerProfileInfoToApiServer",
                                )

                            logger.info { "Successfully uploaded influencer profile info: savedId=${result.savedId}" }
                            result
                        }
                        else -> {
                            logger.error { "Upload failed with msaServiceErrorCode=${response.msaServiceErrorCode}" +
                                    ", errorMessage=${response.errorMessage}, logics=${response.logics}" }
                            throw UploadFailedToImageServerException(
                                logics = "UserProfileApiService - uploadInfluencerProfileInfoToApiServer",
                            )
                        }
                    }
                }

                // Step 2: Change profile image status to save
                userProfileImageApiService.changeProfileStatusToSave(
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
}