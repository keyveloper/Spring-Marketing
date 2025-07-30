package org.example.marketing.service

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import io.github.resilience4j.kotlin.circuitbreaker.executeSuspendFunction
import org.example.marketing.dto.like.request.LikeOrSwitchApiRequest
import org.example.marketing.dto.like.request.UnLikeApiRequest
import org.example.marketing.dto.like.response.*
import org.example.marketing.enums.MSAServiceErrorCode
import org.example.marketing.exception.GetInfluencersByAdFailedException
import org.example.marketing.exception.GetLikedAdsFailedException
import org.example.marketing.exception.LikeFailedException
import org.example.marketing.exception.UnLikeFailedException
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
                            ?: throw LikeFailedException(
                                logics = "LikeApiService.likeOrSwitch: result is null"
                            )

                        logger.info { "Successfully processed like/switch: status=${result.likeStatus}" }
                        result
                    }
                    else -> {
                        logger.error { "Like/switch failed with msaServiceErrorCode=" +
                                "${response.msaServiceErrorCode}, errorMessage=${response.errorMessage}, " +
                                "logics=${response.logics}" }
                        throw LikeFailedException(
                            logics = "LikeApiService.likeOrSwitch: ${response.logics}",
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
     * 좋아요 취소
     *
     * @param influencerId Influencer ID
     * @param advertisementId Advertisement ID
     * @return UnLikeResult
     * @throws MSAErrorException 처리 실패 시
     */
    suspend fun unLike(
        influencerId: UUID,
        advertisementId: Long
    ): UnLikeResult {
        logger.info { "UnLike: influencerId=$influencerId, advertisementId=$advertisementId" }

        return try {
            circuitBreaker.executeSuspendFunction {
                val request = UnLikeApiRequest(
                    influencerId = influencerId,
                    advertisementId = advertisementId
                )

                val response = likeApiServerClient.post()
                    .uri("/api/v1/like/ad/unlike")
                    .bodyValue(request)
                    .retrieve()
                    .awaitBody<UnLikeResponseFromServer>()

                logger.info { "Received response from like-api-server: msaServiceErrorCode=${response.msaServiceErrorCode}" }

                when (response.msaServiceErrorCode) {
                    MSAServiceErrorCode.OK -> {
                        val result = response.result
                            ?: throw UnLikeFailedException(
                                logics = "LikeApiService.unLike: result is null"
                            )

                        logger.info { "Successfully unliked: effectedRow=${result.effectedRow}" }
                        result
                    }
                    else -> {
                        logger.error { "UnLike failed: ${response.errorMessage}" }
                        throw UnLikeFailedException(
                            logics = "LikeApiService.unLike: ${response.logics}",
                            message = response.errorMessage ?: "Failed to unlike"
                        )
                    }
                }
            }
        } catch (ex: Throwable) {
            logger.error { "Failed to unlike: ${ex.message}" }
            throw ex
        }
    }

    /**
     * 인플루언서가 좋아요한 광고 목록 조회
     *
     * @param influencerId Influencer ID
     * @return GetLikedAdsByInfluencerIdResult
     * @throws GetLikedAdsFailedException 조회 실패 시
     */
    suspend fun getLikedAdsByInfluencerId(
        influencerId: UUID
    ): GetLikedAdsByInfluencerIdResult {
        logger.info { "GetLikedAdsByInfluencerId: influencerId=$influencerId" }

        return try {
            circuitBreaker.executeSuspendFunction {
                val response = likeApiServerClient.get()
                    .uri("/api/v1/like/influencer/$influencerId")
                    .retrieve()
                    .awaitBody<GetLikedAdsByInfluencerIdResponseFromServer>()

                logger.info { "Received response from like-api-server: msaServiceErrorCode=${response.msaServiceErrorCode}" }

                when (response.msaServiceErrorCode) {
                    MSAServiceErrorCode.OK -> {
                        val result = response.result
                            ?: throw GetLikedAdsFailedException(
                                logics = "LikeApiService.getLikedAdsByInfluencerId: result is null"
                            )
                        logger.info { "Successfully got liked ads: count=${result.advertisementIds.size}" }
                        result
                    }
                    else -> {
                        logger.error { "GetLikedAdsByInfluencerId failed: ${response.errorMessage}" }
                        throw GetLikedAdsFailedException(
                            logics = "LikeApiService.getLikedAdsByInfluencerId: ${response.logics}",
                            message = response.errorMessage ?: "Failed to get liked advertisements"
                        )
                    }
                }
            }
        } catch (ex: Throwable) {
            logger.error { "Failed to get liked ads by influencer id: ${ex.message}" }
            throw ex
        }
    }

    /**
     * 광고를 좋아요한 인플루언서 목록 조회
     *
     * @param advertisementId Advertisement ID
     * @return GetInfluencersByAdIdResult
     * @throws GetInfluencersByAdFailedException 조회 실패 시
     */
    suspend fun getInfluencersByAdId(
        advertisementId: Long
    ): GetInfluencersByAdIdResult {
        logger.info { "GetInfluencersByAdId: advertisementId=$advertisementId" }

        return try {
            circuitBreaker.executeSuspendFunction {
                val response = likeApiServerClient.get()
                    .uri("/api/v1/like/advertisement/$advertisementId")
                    .retrieve()
                    .awaitBody<GetInfluencersByAdIdResponseFromServer>()

                logger.info { "Received response from like-api-server: msaServiceErrorCode=${response.msaServiceErrorCode}" }

                when (response.msaServiceErrorCode) {
                    MSAServiceErrorCode.OK -> {
                        val result = response.result
                            ?: throw GetInfluencersByAdFailedException(
                                logics = "LikeApiService.getInfluencersByAdId: result is null"
                            )
                        logger.info { "Successfully got influencers: count=${result.influencerIds.size}" }
                        result
                    }
                    else -> {
                        logger.error { "GetInfluencersByAdId failed: ${response.errorMessage}" }
                        throw GetInfluencersByAdFailedException(
                            logics = "LikeApiService.getInfluencersByAdId: ${response.logics}",
                            message = response.errorMessage ?: "Failed to get influencers by advertisement"
                        )
                    }
                }
            }
        } catch (ex: Throwable) {
            logger.error { "Failed to get influencers by ad id: ${ex.message}" }
            throw ex
        }
    }

    // Fallback methods
    private fun likeOrSwitchFallback(
        influencerId: UUID,
        advertisementId: Long,
        ex: Throwable
    ): LikeOrSwitchResult? {
        logger.error { "Fallback triggered for likeOrSwitch: ${ex.message}" }
        return null
    }

    private fun unLikeFallback(
        influencerId: UUID,
        advertisementId: Long,
        ex: Throwable
    ): UnLikeResult {
        logger.error { "Fallback triggered for unLike: ${ex.message}" }
        return UnLikeResult(effectedRow = -1)
    }

    private fun getLikedAdsByInfluencerIdFallback(
        influencerId: UUID,
        ex: Throwable
    ): GetLikedAdsByInfluencerIdResult? {
        logger.error { "Fallback triggered for getLikedAdsByInfluencerId: ${ex.message}" }
        return null
    }

    private fun getInfluencersByAdIdFallback(
        advertisementId: Long,
        ex: Throwable
    ): GetInfluencersByAdIdResult? {
        logger.error { "Fallback triggered for getInfluencersByAdId: ${ex.message}" }
        return null
    }
}
