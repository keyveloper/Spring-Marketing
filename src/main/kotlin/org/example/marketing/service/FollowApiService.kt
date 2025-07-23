package org.example.marketing.service

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import io.github.resilience4j.kotlin.circuitbreaker.executeSuspendFunction
import org.example.marketing.dto.follow.request.FollowOrSwitchApiRequest
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
     * @return FollowOrSwitchResult
     * @throws MSAErrorException 처리 실패 시
     */
    suspend fun followOrSwitch(
        advertiserId: UUID,
        influencerId: UUID
    ): FollowOrSwitchResult {
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
                    .awaitBody<FollowOrSwitchResponseFromServer>()

                logger.info { "Received response from follow-api-server: msaServiceErrorCode=" +
                        "${response.msaServiceErrorCode}, httpStatus=${response.httpStatus}" }

                when (response.msaServiceErrorCode) {
                    MSAServiceErrorCode.OK -> {
                        val result = response.result
                            ?: throw MSAErrorException(
                                logics = "FollowApiService - followOrSwitch: result is null",
                                message = "Failed to follow or switch"
                            )

                        logger.info { "Successfully processed follow/switch: status=${result.followStatus}" }
                        result
                    }
                    else -> {
                        logger.error { "Follow/switch failed with msaServiceErrorCode=" +
                                "${response.msaServiceErrorCode}, errorMessage=${response.errorMessage}, " +
                                "logics=${response.logics}" }
                        throw MSAErrorException(
                            logics = "FollowApiService - followOrSwitch: ${response.logics}",
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
    suspend fun getFollowersByAdvertiserId(advertiserId: UUID): GetFollowersResult {
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
                        val result = response.result
                            ?: throw MSAErrorException(
                                logics = "FollowApiService - getFollowersByAdvertiserId: result is null",
                                message = "Failed to get followers"
                            )

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
    suspend fun getFollowingByInfluencerId(influencerId: UUID): GetFollowingResult {
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
                        val result = response.result
                            ?: throw MSAErrorException(
                                logics = "FollowApiService - getFollowingByInfluencerId: result is null",
                                message = "Failed to get following"
                            )

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
     * 팔로우/언팔로우 실패 시 Fallback 메서드
     */
    suspend fun followOrSwitchFallback(
        advertiserId: UUID,
        influencerId: UUID,
        throwable: Throwable
    ): FollowOrSwitchResult {
        logger.error { "Circuit breaker fallback triggered for followOrSwitch: ${throwable.message}" }
        logger.error { "Failed request details - advertiserId: $advertiserId, influencerId: $influencerId" }
        throw MSAErrorException(
            logics = "FollowApiService - followOrSwitch fallback",
            message = "Failed to follow or switch: ${throwable.message}"
        )
    }

    /**
     * 팔로워 조회 실패 시 Fallback 메서드
     */
    suspend fun getFollowersByAdvertiserIdFallback(
        advertiserId: UUID,
        throwable: Throwable
    ): GetFollowersResult {
        logger.error { "Circuit breaker fallback triggered for getFollowersByAdvertiserId: ${throwable.message}" }
        logger.error { "Failed request details - advertiserId: $advertiserId" }
        throw MSAErrorException(
            logics = "FollowApiService - getFollowersByAdvertiserId fallback",
            message = "Failed to get followers: ${throwable.message}"
        )
    }

    /**
     * 팔로잉 조회 실패 시 Fallback 메서드
     */
    suspend fun getFollowingByInfluencerIdFallback(
        influencerId: UUID,
        throwable: Throwable
    ): GetFollowingResult {
        logger.error { "Circuit breaker fallback triggered for getFollowingByInfluencerId: ${throwable.message}" }
        logger.error { "Failed request details - influencerId: $influencerId" }
        throw MSAErrorException(
            logics = "FollowApiService - getFollowingByInfluencerId fallback",
            message = "Failed to get following: ${throwable.message}"
        )
    }
}
