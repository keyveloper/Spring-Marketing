package org.example.marketing.service

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import io.github.resilience4j.kotlin.circuitbreaker.executeSuspendFunction
import org.example.marketing.dto.like.request.LikeOrSwitchApiRequest
import org.example.marketing.dto.like.response.LikeOrSwitchResponseFromServer
import org.example.marketing.dto.like.response.LikeOrSwitchResult
import org.example.marketing.enums.MSAServiceErrorCode
import org.example.marketing.exception.MSAErrorException
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import java.util.UUID

/**
 * Like API Server와 통신하는 서비스
 * 좋아요/좋아요 취소 기능을 제공합니다.
 */
@Service
class LikeApiService(
    @Qualifier("likeApiServerClient") private val likeApiServerClient: WebClient,
    private val circuitBreakerRegistry: CircuitBreakerRegistry
) {
    private val logger = KotlinLogging.logger {}
    private val circuitBreaker = circuitBreakerRegistry.circuitBreaker("likeApiCircuitBreaker")

    /**
     * 좋아요 또는 좋아요 취소 토글
     *
     * @param influencerId Influencer ID
     * @param advertisementId Advertisement ID
     * @return LikeOrSwitchResult
     * @throws MSAErrorException 처리 실패 시
     */
    suspend fun likeOrSwitch(
        influencerId: UUID,
        advertisementId: Long
    ): LikeOrSwitchResult {
        logger.info { "Like or switch: influencerId=$influencerId, advertisementId=$advertisementId" }

        return try {
            circuitBreaker.executeSuspendFunction {
                val request = LikeOrSwitchApiRequest(
                    influencerId = influencerId,
                    advertisementId = advertisementId
                )

                val response = likeApiServerClient.post()
                    .uri("/api/v1/like/ad")
                    .bodyValue(request)
                    .retrieve()
                    .awaitBody<LikeOrSwitchResponseFromServer>()

                logger.info { "Received response from like-api-server: msaServiceErrorCode=" +
                        "${response.msaServiceErrorCode}, httpStatus=${response.httpStatus}" }

                when (response.msaServiceErrorCode) {
                    MSAServiceErrorCode.OK -> {
                        val result = response.result
                            ?: throw MSAErrorException(
                                logics = "LikeApiService - likeOrSwitch: result is null",
                                message = "Failed to like or switch"
                            )

                        logger.info { "Successfully processed like/switch: status=${result.likeStatus}" }
                        result
                    }
                    else -> {
                        logger.error { "Like/switch failed with msaServiceErrorCode=" +
                                "${response.msaServiceErrorCode}, errorMessage=${response.errorMessage}, " +
                                "logics=${response.logics}" }
                        throw MSAErrorException(
                            logics = "LikeApiService - likeOrSwitch: ${response.logics}",
                            message = response.errorMessage ?: "Failed to like or switch"
                        )
                    }
                }
            }
        } catch (ex: Throwable) {
            logger.error { "Failed to like or switch: ${ex.message}" }
            throw ex
        }
    }

    /**
     * 좋아요/좋아요 취소 실패 시 Fallback 메서드
     */
    suspend fun likeOrSwitchFallback(
        influencerId: UUID,
        advertisementId: Long,
        throwable: Throwable
    ): LikeOrSwitchResult {
        logger.error { "Circuit breaker fallback triggered for likeOrSwitch: ${throwable.message}" }
        logger.error { "Failed request details - influencerId: $influencerId, advertisementId: $advertisementId" }
        throw MSAErrorException(
            logics = "LikeApiService - likeOrSwitch fallback",
            message = "Failed to like or switch: ${throwable.message}"
        )
    }
}
