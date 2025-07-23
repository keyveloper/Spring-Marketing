package org.example.marketing.service

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import io.github.resilience4j.kotlin.circuitbreaker.executeSuspendFunction
import org.example.marketing.dto.timeline.request.DeleteTimelineAdApiRequest
import org.example.marketing.dto.timeline.request.GetTimelineAdsApiRequest
import org.example.marketing.dto.timeline.request.UploadTimelineAdApiRequest
import org.example.marketing.dto.timeline.response.*
import org.example.marketing.enums.MSAServiceErrorCode
import org.example.marketing.enums.TimelineCursor
import org.example.marketing.exception.MSAErrorException
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import java.util.UUID

/**
 * Timeline API Server와 통신하는 서비스
 * 타임라인 광고 조회, 추가, 삭제 기능을 제공합니다.
 */
@Service
class TimelineApiService(
    @Qualifier("timelineApiServerClient") private val timelineApiServerClient: WebClient,
    private val circuitBreakerRegistry: CircuitBreakerRegistry
) {
    private val logger = KotlinLogging.logger {}
    private val circuitBreaker = circuitBreakerRegistry.circuitBreaker("timelineApiCircuitBreaker")

    /**
     * 타임라인 광고 목록 조회
     *
     * @param influencerId Influencer ID
     * @param cursor 커서 방향 (FORWARD/BACKWARD)
     * @param pivotTime 기준 시간
     * @return GetTimelineAdsResult
     * @throws MSAErrorException 조회 실패 시
     */
    suspend fun getTimelineAds(
        influencerId: UUID,
        cursor: TimelineCursor,
        pivotTime: Long?
    ): GetTimelineAdsResult {
        logger.info { "Getting timeline ads: influencerId=$influencerId, cursor=$cursor, pivotTime=$pivotTime" }

        return try {
            circuitBreaker.executeSuspendFunction {
                val response = timelineApiServerClient.get()
                    .uri { uriBuilder ->
                        uriBuilder.path("/api/v1/timeline/ads")
                            .queryParam("influencerId", influencerId)
                            .queryParam("cursor", cursor.name)
                            .apply {
                                pivotTime?.let { queryParam("pivotTime", it) }
                            }
                            .build()
                    }
                    .retrieve()
                    .awaitBody<GetTimelineAdsResponseFromServer>()

                logger.info { "Received response from timeline-api-server: msaServiceErrorCode=" +
                        "${response.msaServiceErrorCode}, httpStatus=${response.httpStatus}" }

                when (response.msaServiceErrorCode) {
                    MSAServiceErrorCode.OK -> {
                        val result = response.result
                            ?: throw MSAErrorException(
                                logics = "TimelineApiService - getTimelineAds: result is null",
                                message = "Failed to get timeline ads"
                            )

                        logger.info { "Successfully retrieved ${result.timelineAds.size} timeline ads" }
                        result
                    }
                    else -> {
                        logger.error { "Timeline ads retrieval failed with msaServiceErrorCode=" +
                                "${response.msaServiceErrorCode}, errorMessage=${response.errorMessage}, " +
                                "logics=${response.logics}" }
                        throw MSAErrorException(
                            logics = "TimelineApiService - getTimelineAds: ${response.logics}",
                            message = response.errorMessage ?: "Failed to get timeline ads"
                        )
                    }
                }
            }
        } catch (ex: Throwable) {
            logger.error { "Failed to get timeline ads: ${ex.message}" }
            throw ex
        }
    }

    /**
     * 타임라인에 광고 추가
     *
     * @param influencerId Influencer ID
     * @param advertisementId Advertisement ID
     * @return UploadTimelineAdResult
     * @throws MSAErrorException 추가 실패 시
     */
    suspend fun uploadTimelineAd(
        influencerId: UUID,
        advertisementId: Long
    ): UploadTimelineAdResult {
        logger.info { "Uploading timeline ad: influencerId=$influencerId, advertisementId=$advertisementId" }

        return try {
            circuitBreaker.executeSuspendFunction {
                val request = UploadTimelineAdApiRequest(
                    influencerId = influencerId,
                    advertisementId = advertisementId
                )

                val response = timelineApiServerClient.post()
                    .uri("/api/v1/timeline/ads")
                    .bodyValue(request)
                    .retrieve()
                    .awaitBody<UploadTimelineAdResponseFromServer>()

                logger.info { "Received response from timeline-api-server: msaServiceErrorCode=" +
                        "${response.msaServiceErrorCode}, httpStatus=${response.httpStatus}" }

                when (response.msaServiceErrorCode) {
                    MSAServiceErrorCode.OK -> {
                        val result = response.result
                            ?: throw MSAErrorException(
                                logics = "TimelineApiService - uploadTimelineAd: result is null",
                                message = "Failed to upload timeline ad"
                            )

                        logger.info { "Successfully uploaded timeline ad: advertisementId=$advertisementId" }
                        result
                    }
                    else -> {
                        logger.error { "Timeline ad upload failed with msaServiceErrorCode=" +
                                "${response.msaServiceErrorCode}, errorMessage=${response.errorMessage}, " +
                                "logics=${response.logics}" }
                        throw MSAErrorException(
                            logics = "TimelineApiService - uploadTimelineAd: ${response.logics}",
                            message = response.errorMessage ?: "Failed to upload timeline ad"
                        )
                    }
                }
            }
        } catch (ex: Throwable) {
            logger.error { "Failed to upload timeline ad: ${ex.message}" }
            throw ex
        }
    }

    /**
     * 타임라인에서 광고 삭제
     *
     * @param influencerId Influencer ID
     * @param advertisementId Advertisement ID
     * @return 삭제된 행 수
     * @throws MSAErrorException 삭제 실패 시
     */
    suspend fun deleteTimelineAd(
        influencerId: UUID,
        advertisementId: Long
    ): Int {
        logger.info { "Deleting timeline ad: influencerId=$influencerId, advertisementId=$advertisementId" }

        return try {
            circuitBreaker.executeSuspendFunction {
                val response = timelineApiServerClient.delete()
                    .uri { uriBuilder ->
                        uriBuilder.path("/api/v1/timeline/ads")
                            .queryParam("influencerId", influencerId)
                            .queryParam("advertisementId", advertisementId)
                            .build()
                    }
                    .retrieve()
                    .awaitBody<DeleteTimelineAdResponseFromServer>()

                logger.info { "Received response from timeline-api-server: msaServiceErrorCode=" +
                        "${response.msaServiceErrorCode}, httpStatus=${response.httpStatus}" }

                when (response.msaServiceErrorCode) {
                    MSAServiceErrorCode.OK -> {
                        logger.info { "Successfully deleted timeline ad: deletedRow=${response.deletedRow}" }
                        response.deletedRow
                    }
                    else -> {
                        logger.error { "Timeline ad deletion failed with msaServiceErrorCode=" +
                                "${response.msaServiceErrorCode}, errorMessage=${response.errorMessage}, " +
                                "logics=${response.logics}" }
                        throw MSAErrorException(
                            logics = "TimelineApiService - deleteTimelineAd: ${response.logics}",
                            message = response.errorMessage ?: "Failed to delete timeline ad"
                        )
                    }
                }
            }
        } catch (ex: Throwable) {
            logger.error { "Failed to delete timeline ad: ${ex.message}" }
            throw ex
        }
    }

    /**
     * 타임라인 광고 조회 실패 시 Fallback 메서드
     */
    suspend fun getTimelineAdsFallback(
        influencerId: UUID,
        cursor: TimelineCursor,
        pivotTime: Long?,
        throwable: Throwable
    ): GetTimelineAdsResult {
        logger.error { "Circuit breaker fallback triggered for getTimelineAds: ${throwable.message}" }
        logger.error { "Failed request details - influencerId: $influencerId, cursor: $cursor, pivotTime: $pivotTime" }
        throw MSAErrorException(
            logics = "TimelineApiService - getTimelineAds fallback",
            message = "Failed to get timeline ads: ${throwable.message}"
        )
    }

    /**
     * 타임라인 광고 추가 실패 시 Fallback 메서드
     */
    suspend fun uploadTimelineAdFallback(
        influencerId: UUID,
        advertisementId: Long,
        throwable: Throwable
    ): UploadTimelineAdResult {
        logger.error { "Circuit breaker fallback triggered for uploadTimelineAd: ${throwable.message}" }
        logger.error { "Failed request details - influencerId: $influencerId, advertisementId: $advertisementId" }
        throw MSAErrorException(
            logics = "TimelineApiService - uploadTimelineAd fallback",
            message = "Failed to upload timeline ad: ${throwable.message}"
        )
    }

    /**
     * 타임라인 광고 삭제 실패 시 Fallback 메서드
     */
    suspend fun deleteTimelineAdFallback(
        influencerId: UUID,
        advertisementId: Long,
        throwable: Throwable
    ): Int {
        logger.error { "Circuit breaker fallback triggered for deleteTimelineAd: ${throwable.message}" }
        logger.error { "Failed request details - influencerId: $influencerId, advertisementId: $advertisementId" }
        throw MSAErrorException(
            logics = "TimelineApiService - deleteTimelineAd fallback",
            message = "Failed to delete timeline ad: ${throwable.message}"
        )
    }
}
