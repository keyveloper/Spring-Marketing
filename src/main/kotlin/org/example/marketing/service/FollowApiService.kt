package org.example.marketing.service

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import io.github.resilience4j.kotlin.circuitbreaker.executeSuspendFunction
import org.example.marketing.dto.follow.request.FollowOrSwitchApiRequest
import org.example.marketing.dto.follow.request.UnFollowApiRequest
import org.example.marketing.dto.follow.response.*
import org.example.marketing.enums.MSAServiceErrorCode
import org.example.marketing.exception.MSAErrorException
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import java.util.UUID

/**
 * Follow API Server와 통신하는 서비스
 * 팔로우/언팔로우 및 팔로워/팔로잉 조회 기능을 제공합니다.
 */
@Service
class FollowApiService(
    @Qualifier("followApiServerClient") private val followApiServerClient: WebClient,
    private val circuitBreakerRegistry: CircuitBreakerRegistry
) {
    private val logger = KotlinLogging.logger {}
    private val circuitBreaker = circuitBreakerRegistry.circuitBreaker("followApiCircuitBreaker")

    /**
     * 팔로우 또는 언팔로우 토글
     *
     * @param advertiserId Advertiser ID
     * @param influencerId Influencer ID
     * @return FollowResult
     * @throws MSAErrorException 처리 실패 시
     */
    suspend fun follow(
        advertiserId: UUID,
        influencerId: UUID
    ): FollowResult? {
        logger.info { "Follow or switch: advertiserId=$advertiserId, influencerId=$influencerId" }

        return try {
            circuitBreaker.executeSuspendFunction {
                val request = FollowOrSwitchApiRequest(
                    advertiserId = advertiserId,
                    influencerId = influencerId
                )

                val response = followApiServerClient.post()
                    .uri("/api/follow")
                    .bodyValue(request)
                    .retrieve()
                    .awaitBody<FollowResponseFromServer>()

                logger.info { "Received response from follow-api-server: msaServiceErrorCode=" +
                        "${response.msaServiceErrorCode}, httpStatus=${response.httpStatus}" }

                when (response.msaServiceErrorCode) {
                    MSAServiceErrorCode.OK -> {
                        val resultFromServer = response.result
                        val result = FollowResult.of(resultFromServer)
                        logger.info { "Successfully processed follow/switch: status=${result.followStatus}" }
                        result
                    }
                    else -> {
                        logger.error { "Follow/switch failed with msaServiceErrorCode=" +
                                "${response.msaServiceErrorCode}, errorMessage=${response.errorMessage}, " +
                                "logics=${response.logics}" }
                        throw MSAErrorException(
                            logics = "FollowApiService - follow: ${response.logics}",
                            message = response.errorMessage ?: "Failed to follow or switch"
                        )
                    }
                }
            }
        } catch (ex: Throwable) {
            logger.error { "Failed to follow or switch: ${ex.message}" }
            throw ex
        }
    }

    /**
     * Advertiser의 팔로워 목록 조회
     *
     * @param advertiserId Advertiser ID
     * @return GetFollowersResult
     * @throws MSAErrorException 조회 실패 시
     */
    suspend fun getFollowersByAdvertiserId(advertiserId: UUID): GetFollowersResult? {
        logger.info { "Getting followers for advertiser: advertiserId=$advertiserId" }

        return try {
            circuitBreaker.executeSuspendFunction {
                val response = followApiServerClient.get()
                    .uri { uriBuilder ->
                        uriBuilder.path("/api/follow/followers")
                            .queryParam("advertiserId", advertiserId)
                            .build()
                    }
                    .retrieve()
                    .awaitBody<GetFollowersResponseFromServer>()

                logger.info { "Received response from follow-api-server: msaServiceErrorCode=" +
                        "${response.msaServiceErrorCode}, httpStatus=${response.httpStatus}" }

                when (response.msaServiceErrorCode) {
                    MSAServiceErrorCode.OK -> {
                        val resultFromServer = response.result
                            ?: throw MSAErrorException(
                                logics = "FollowApiService - getFollowersByAdvertiserId: result is null",
                                message = "Failed to get followers"
                            )

                        val result = GetFollowersResult.of(resultFromServer)
                        logger.info { "Successfully retrieved ${result.followers.size} followers" }
                        result
                    }
                    else -> {
                        logger.error { "Get followers failed with msaServiceErrorCode=" +
                                "${response.msaServiceErrorCode}, errorMessage=${response.errorMessage}, " +
                                "logics=${response.logics}" }
                        throw MSAErrorException(
                            logics = "FollowApiService - getFollowersByAdvertiserId: ${response.logics}",
                            message = response.errorMessage ?: "Failed to get followers"
                        )
                    }
                }
            }
        } catch (ex: Throwable) {
            logger.error { "Failed to get followers: ${ex.message}" }
            throw ex
        }
    }

    /**
     * Influencer의 팔로잉 목록 조회
     *
     * @param influencerId Influencer ID
     * @return GetFollowingResult
     * @throws MSAErrorException 조회 실패 시
     */
    suspend fun getFollowingByInfluencerId(influencerId: UUID): GetFollowingResult? {
        logger.info { "Getting following for influencer: influencerId=$influencerId" }

        return try {
            circuitBreaker.executeSuspendFunction {
                val response = followApiServerClient.get()
                    .uri { uriBuilder ->
                        uriBuilder.path("/api/follow/following")
                            .queryParam("influencerId", influencerId)
                            .build()
                    }
                    .retrieve()
                    .awaitBody<GetFollowingResponseFromServer>()

                logger.info { "Received response from follow-api-server: msaServiceErrorCode=" +
                        "${response.msaServiceErrorCode}, httpStatus=${response.httpStatus}" }

                when (response.msaServiceErrorCode) {
                    MSAServiceErrorCode.OK -> {
                        val resultFromServer = response.result
                            ?: throw MSAErrorException(
                                logics = "FollowApiService - getFollowingByInfluencerId: result is null",
                                message = "Failed to get following"
                            )

                        val result = GetFollowingResult.of(resultFromServer)
                        logger.info { "Successfully retrieved ${result.following.size} following" }
                        result
                    }
                    else -> {
                        logger.error { "Get following failed with msaServiceErrorCode=" +
                                "${response.msaServiceErrorCode}, errorMessage=${response.errorMessage}, " +
                                "logics=${response.logics}" }
                        throw MSAErrorException(
                            logics = "FollowApiService - getFollowingByInfluencerId: ${response.logics}",
                            message = response.errorMessage ?: "Failed to get following"
                        )
                    }
                }
            }
        } catch (ex: Throwable) {
            logger.error { "Failed to get following: ${ex.message}" }
            throw ex
        }
    }

    /**
     * 언팔로우
     *
     * @param advertiserId Advertiser ID
     * @param influencerId Influencer ID
     * @return UnFollowResult
     * @throws MSAErrorException 처리 실패 시
     */
    suspend fun unFollow(
        advertiserId: UUID,
        influencerId: UUID
    ): UnFollowResult? {
        logger.info { "Unfollow: advertiserId=$advertiserId, influencerId=$influencerId" }

        return try {
            circuitBreaker.executeSuspendFunction {
                val request = UnFollowApiRequest(
                    advertiserId = advertiserId,
                    influencerId = influencerId
                )

                val response = followApiServerClient.post()
                    .uri("/api/follow/unfollow")
                    .bodyValue(request)
                    .retrieve()
                    .awaitBody<UnFollowResponseFromServer>()

                logger.info { "Received response from follow-api-server: msaServiceErrorCode=" +
                        "${response.msaServiceErrorCode}, httpStatus=${response.httpStatus}" }

                when (response.msaServiceErrorCode) {
                    MSAServiceErrorCode.OK -> {
                        val resultFromServer = response.result
                        val result = UnFollowResult.of(resultFromServer)
                        logger.info { "Successfully unfollowed: effectedRow=${result.effectedRow}" }
                        result
                    }
                    else -> {
                        logger.error { "Unfollow failed with msaServiceErrorCode=" +
                                "${response.msaServiceErrorCode}, errorMessage=${response.errorMessage}, " +
                                "logics=${response.logics}" }
                        throw MSAErrorException(
                            logics = "FollowApiService - unFollow: ${response.logics}",
                            message = response.errorMessage ?: "Failed to unfollow"
                        )
                    }
                }
            }
        } catch (ex: Throwable) {
            logger.error { "Failed to unfollow: ${ex.message}" }
            throw ex
        }
    }

    /**
     * 팔로우/언팔로우 실패 시 Fallback 메서드
     */
    suspend fun followFallback(
        advertiserId: UUID,
        influencerId: UUID,
        throwable: Throwable
    ): FollowResult? {
        logger.error { "Circuit breaker fallback triggered for follow: ${throwable.message}" }
        logger.error { "Failed request details - advertiserId: $advertiserId, influencerId: $influencerId" }
        return null
    }

    /**
     * 언팔로우 실패 시 Fallback 메서드
     */
    suspend fun unFollowFallback(
        advertiserId: UUID,
        influencerId: UUID,
        throwable: Throwable
    ): UnFollowResult? {
        logger.error { "Circuit breaker fallback triggered for unFollow: ${throwable.message}" }
        logger.error { "Failed request details - advertiserId: $advertiserId, influencerId: $influencerId" }
        return null
    }

    /**
     * 팔로워 조회 실패 시 Fallback 메서드
     */
    suspend fun getFollowersByAdvertiserIdFallback(
        advertiserId: UUID,
        throwable: Throwable
    ): GetFollowersResult? {
        logger.error { "Circuit breaker fallback triggered for getFollowersByAdvertiserId: ${throwable.message}" }
        logger.error { "Failed request details - advertiserId: $advertiserId" }
        return null
    }

    /**
     * 팔로잉 조회 실패 시 Fallback 메서드
     */
    suspend fun getFollowingByInfluencerIdFallback(
        influencerId: UUID,
        throwable: Throwable
    ): GetFollowingResult? {
        logger.error { "Circuit breaker fallback triggered for getFollowingByInfluencerId: ${throwable.message}" }
        logger.error { "Failed request details - influencerId: $influencerId" }
        return null
    }
}
