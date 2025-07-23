package org.example.marketing.service

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import io.github.resilience4j.kotlin.circuitbreaker.executeSuspendFunction
import org.example.marketing.dto.noti.request.UploadNotiToAdvertiserApiRequest
import org.example.marketing.dto.noti.request.UploadNotiToInfluencerApiRequest
import org.example.marketing.dto.noti.response.*
import org.example.marketing.enums.MSAServiceErrorCode
import org.example.marketing.exception.MSAErrorException
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import java.util.UUID

/**
 * Noti API Server와 통신하는 서비스
 * 알림 생성 및 조회 기능을 제공합니다.
 */
@Service
class NotiApiService(
    @Qualifier("notiApiServerClient") private val notiApiServerClient: WebClient,
    private val circuitBreakerRegistry: CircuitBreakerRegistry
) {
    private val logger = KotlinLogging.logger {}
    private val circuitBreaker = circuitBreakerRegistry.circuitBreaker("notiApiCircuitBreaker")

    /**
     * Advertiser에게 알림 생성
     *
     * @param request UploadNotiToAdvertiserApiRequest
     * @return UploadNotiToAdvertiserResult
     * @throws MSAErrorException 알림 생성 실패 시
     */
    suspend fun createNotiToAdvertiser(request: UploadNotiToAdvertiserApiRequest): UploadNotiToAdvertiserResult {
        logger.info { "Creating notification to advertiser: advertiserId=${request.advertiserId}, type=${request.notiToAdvertiserType}" }

        return try {
            circuitBreaker.executeSuspendFunction {
                val response = notiApiServerClient.post()
                    .uri("/api/v1/noti-to-advertiser")
                    .bodyValue(request)
                    .retrieve()
                    .awaitBody<UploadNotiToAdvertiserResponseFromServer>()

                logger.info { "Received response from noti-api-server: msaServiceErrorCode=" +
                        "${response.msaServiceErrorCode}, httpStatus=${response.httpStatus}" }

                when (response.msaServiceErrorCode) {
                    MSAServiceErrorCode.OK -> {
                        val result = response.result
                            ?: throw MSAErrorException(
                                logics = "NotiApiService - createNotiToAdvertiser: result is null",
                                message = "Failed to create notification to advertiser"
                            )

                        logger.info { "Successfully created notification to advertiser: notiId=${result.notiId}" }
                        result
                    }
                    else -> {
                        logger.error { "Notification creation failed with msaServiceErrorCode=" +
                                "${response.msaServiceErrorCode}, errorMessage=${response.errorMessage}, " +
                                "logics=${response.logics}" }
                        throw MSAErrorException(
                            logics = "NotiApiService - createNotiToAdvertiser: ${response.logics}",
                            message = response.errorMessage ?: "Failed to create notification to advertiser"
                        )
                    }
                }
            }
        } catch (ex: Throwable) {
            logger.error { "Failed to create notification to advertiser: ${ex.message}" }
            throw ex
        }
    }

    /**
     * Influencer에게 알림 생성
     *
     * @param request UploadNotiToInfluencerApiRequest
     * @return UploadNotiToInfluencerResult
     * @throws MSAErrorException 알림 생성 실패 시
     */
    suspend fun createNotiToInfluencer(request: UploadNotiToInfluencerApiRequest): UploadNotiToInfluencerResult {
        logger.info { "Creating notification to influencer: influencerId=${request.influencerId}, type=${request.notiToInfluencerType}" }

        return try {
            circuitBreaker.executeSuspendFunction {
                val response = notiApiServerClient.post()
                    .uri("/api/v1/noti-to-influencer")
                    .bodyValue(request)
                    .retrieve()
                    .awaitBody<UploadNotiToInfluencerResponseFromServer>()

                logger.info { "Received response from noti-api-server: msaServiceErrorCode=" +
                        "${response.msaServiceErrorCode}, httpStatus=${response.httpStatus}" }

                when (response.msaServiceErrorCode) {
                    MSAServiceErrorCode.OK -> {
                        val result = response.result
                            ?: throw MSAErrorException(
                                logics = "NotiApiService - createNotiToInfluencer: result is null",
                                message = "Failed to create notification to influencer"
                            )

                        logger.info { "Successfully created notification to influencer: notiId=${result.notiId}" }
                        result
                    }
                    else -> {
                        logger.error { "Notification creation failed with msaServiceErrorCode=" +
                                "${response.msaServiceErrorCode}, errorMessage=${response.errorMessage}, " +
                                "logics=${response.logics}" }
                        throw MSAErrorException(
                            logics = "NotiApiService - createNotiToInfluencer: ${response.logics}",
                            message = response.errorMessage ?: "Failed to create notification to influencer"
                        )
                    }
                }
            }
        } catch (ex: Throwable) {
            logger.error { "Failed to create notification to influencer: ${ex.message}" }
            throw ex
        }
    }

    /**
     * Advertiser의 알림 목록 조회
     *
     * @param advertiserId Advertiser ID
     * @return List<NotiToAdvertiserInfo>
     * @throws MSAErrorException 조회 실패 시
     */
    suspend fun getNotiToAdvertisersByAdvertiserId(advertiserId: UUID): List<NotiToAdvertiserInfo> {
        logger.info { "Getting notifications for advertiser: advertiserId=$advertiserId" }

        return try {
            circuitBreaker.executeSuspendFunction {
                val response = notiApiServerClient.get()
                    .uri("/api/v1/noti-to-advertiser/advertiser/$advertiserId")
                    .retrieve()
                    .awaitBody<GetNotiToAdvertisersByAdvertiserIdResponseFromServer>()

                logger.info { "Received response from noti-api-server: msaServiceErrorCode=" +
                        "${response.msaServiceErrorCode}, httpStatus=${response.httpStatus}" }

                when (response.msaServiceErrorCode) {
                    MSAServiceErrorCode.OK -> {
                        val result = response.result ?: emptyList()
                        logger.info { "Successfully retrieved ${result.size} notifications for advertiser" }
                        result
                    }
                    else -> {
                        logger.error { "Notification retrieval failed with msaServiceErrorCode=" +
                                "${response.msaServiceErrorCode}, errorMessage=${response.errorMessage}, " +
                                "logics=${response.logics}" }
                        throw MSAErrorException(
                            logics = "NotiApiService - getNotiToAdvertisersByAdvertiserId: ${response.logics}",
                            message = response.errorMessage ?: "Failed to retrieve notifications for advertiser"
                        )
                    }
                }
            }
        } catch (ex: Throwable) {
            logger.error { "Failed to get notifications for advertiser: ${ex.message}" }
            throw ex
        }
    }

    /**
     * Influencer의 알림 목록 조회
     *
     * @param influencerId Influencer ID
     * @return List<NotiToInfluencerInfo>
     * @throws MSAErrorException 조회 실패 시
     */
    suspend fun getNotiToInfluencersByInfluencerId(influencerId: UUID): List<NotiToInfluencerInfo> {
        logger.info { "Getting notifications for influencer: influencerId=$influencerId" }

        return try {
            circuitBreaker.executeSuspendFunction {
                val response = notiApiServerClient.get()
                    .uri("/api/v1/noti-to-influencer/influencer/$influencerId")
                    .retrieve()
                    .awaitBody<GetNotiToInfluencersByInfluencerIdResponseFromServer>()

                logger.info { "Received response from noti-api-server: msaServiceErrorCode=" +
                        "${response.msaServiceErrorCode}, httpStatus=${response.httpStatus}" }

                when (response.msaServiceErrorCode) {
                    MSAServiceErrorCode.OK -> {
                        val result = response.result ?: emptyList()
                        logger.info { "Successfully retrieved ${result.size} notifications for influencer" }
                        result
                    }
                    else -> {
                        logger.error { "Notification retrieval failed with msaServiceErrorCode=" +
                                "${response.msaServiceErrorCode}, errorMessage=${response.errorMessage}, " +
                                "logics=${response.logics}" }
                        throw MSAErrorException(
                            logics = "NotiApiService - getNotiToInfluencersByInfluencerId: ${response.logics}",
                            message = response.errorMessage ?: "Failed to retrieve notifications for influencer"
                        )
                    }
                }
            }
        } catch (ex: Throwable) {
            logger.error { "Failed to get notifications for influencer: ${ex.message}" }
            throw ex
        }
    }

    /**
     * Advertiser 알림 생성 실패 시 Fallback 메서드
     */
    suspend fun createNotiToAdvertiserFallback(
        request: UploadNotiToAdvertiserApiRequest,
        throwable: Throwable
    ): UploadNotiToAdvertiserResult {
        logger.error { "Circuit breaker fallback triggered for createNotiToAdvertiser: ${throwable.message}" }
        logger.error { "Failed request details - advertiserId: ${request.advertiserId}, type: ${request.notiToAdvertiserType}" }
        throw MSAErrorException(
            logics = "NotiApiService - createNotiToAdvertiser fallback",
            message = "Failed to create notification to advertiser: ${throwable.message}"
        )
    }

    /**
     * Influencer 알림 생성 실패 시 Fallback 메서드
     */
    suspend fun createNotiToInfluencerFallback(
        request: UploadNotiToInfluencerApiRequest,
        throwable: Throwable
    ): UploadNotiToInfluencerResult {
        logger.error { "Circuit breaker fallback triggered for createNotiToInfluencer: ${throwable.message}" }
        logger.error { "Failed request details - influencerId: ${request.influencerId}, type: ${request.notiToInfluencerType}" }
        throw MSAErrorException(
            logics = "NotiApiService - createNotiToInfluencer fallback",
            message = "Failed to create notification to influencer: ${throwable.message}"
        )
    }

    /**
     * Advertiser 알림 조회 실패 시 Fallback 메서드
     */
    suspend fun getNotiToAdvertisersByAdvertiserIdFallback(
        advertiserId: UUID,
        throwable: Throwable
    ): List<NotiToAdvertiserInfo> {
        logger.error { "Circuit breaker fallback triggered for getNotiToAdvertisersByAdvertiserId: ${throwable.message}" }
        logger.error { "Failed request details - advertiserId: $advertiserId" }
        throw MSAErrorException(
            logics = "NotiApiService - getNotiToAdvertisersByAdvertiserId fallback",
            message = "Failed to retrieve notifications for advertiser: ${throwable.message}"
        )
    }

    /**
     * Influencer 알림 조회 실패 시 Fallback 메서드
     */
    suspend fun getNotiToInfluencersByInfluencerIdFallback(
        influencerId: UUID,
        throwable: Throwable
    ): List<NotiToInfluencerInfo> {
        logger.error { "Circuit breaker fallback triggered for getNotiToInfluencersByInfluencerId: ${throwable.message}" }
        logger.error { "Failed request details - influencerId: $influencerId" }
        throw MSAErrorException(
            logics = "NotiApiService - getNotiToInfluencersByInfluencerId fallback",
            message = "Failed to retrieve notifications for influencer: ${throwable.message}"
        )
    }
}
