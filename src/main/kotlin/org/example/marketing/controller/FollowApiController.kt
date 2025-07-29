package org.example.marketing.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.dto.follow.request.FollowRequestFromClient
import org.example.marketing.dto.follow.request.UnFollowRequestFromClient
import org.example.marketing.dto.follow.response.FollowResponseToClient
import org.example.marketing.dto.follow.response.GetFollowersResponseToClient
import org.example.marketing.dto.follow.response.GetFollowingResponseToClient
import org.example.marketing.dto.follow.response.UnFollowResponseToClient
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.service.FollowApiService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

/**
 * Follow API Controller
 * 팔로우/언팔로우 및 팔로워/팔로잉 조회 엔드포인트를 제공합니다.
 */
@RestController
@RequestMapping("/api/v1/follow")
class FollowApiController(
    private val followApiService: FollowApiService
) {
    private val logger = KotlinLogging.logger {}

    /**
     * 팔로우 또는 언팔로우 토글
     *
     * POST /api/v1/follow
     *
     * @param requestFromClient FollowOrSwitchRequestFromClient
     * @return FollowOrSwitchResponseToClient
     */
    @PostMapping
    suspend fun follow(
        @RequestBody requestFromClient: FollowRequestFromClient
    ): ResponseEntity<FollowResponseToClient> {
        logger.info { "POST /api/v1/follow - advertiserId=${requestFromClient.advertiserId}, influencerId=${requestFromClient.influencerId}" }

        val result = followApiService.follow(
            advertiserId = requestFromClient.advertiserId,
            influencerId = requestFromClient.influencerId
        )

        val responseToClient = FollowResponseToClient(
            frontErrorCode = FrontErrorCode.OK.code,
            errorMessage = FrontErrorCode.OK.message,
            result = result
        )

        logger.info { "Successfully processed follow/switch: status=${result?.followStatus}" }
        return ResponseEntity.ok(responseToClient)
    }

    /**
     * 언팔로우
     *
     * POST /api/v1/follow/unfollow
     *
     * @param requestFromClient UnFollowRequestFromClient
     * @return UnFollowResponseToClient
     */
    @PostMapping("/unfollow")
    suspend fun unFollow(
        @RequestBody requestFromClient: UnFollowRequestFromClient
    ): ResponseEntity<UnFollowResponseToClient> {
        logger.info { "POST /api/v1/follow/unfollow - advertiserId=${requestFromClient.advertiserId}, influencerId=${requestFromClient.influencerId}" }

        val result = followApiService.unFollow(
            advertiserId = requestFromClient.advertiserId,
            influencerId = requestFromClient.influencerId
        )

        val responseToClient = UnFollowResponseToClient(
            frontErrorCode = FrontErrorCode.OK.code,
            errorMessage = FrontErrorCode.OK.message,
            result = result
        )

        logger.info { "Successfully unfollowed: effectedRow=${result?.effectedRow ?: 0}" }
        return ResponseEntity.ok(responseToClient)
    }

    /**
     * Advertiser의 팔로워 목록 조회
     *
     * GET /api/v1/follow/followers/{advertiserId}
     *
     * @param advertiserId Advertiser ID
     * @return GetFollowersResponseToClient
     */
    @GetMapping("/followers/{advertiserId}")
    suspend fun getFollowersByAdvertiserId(
        @PathVariable advertiserId: UUID
    ): ResponseEntity<GetFollowersResponseToClient> {
        logger.info { "GET /api/v1/follow/followers/$advertiserId" }

        val result = followApiService.getFollowersByAdvertiserId(advertiserId)

        val responseToClient = GetFollowersResponseToClient(
            frontErrorCode = FrontErrorCode.OK.code,
            errorMessage = FrontErrorCode.OK.message,
            result = result
        )

        logger.info { "Successfully retrieved ${result?.followers?.size ?: 0} followers for advertiser" }
        return ResponseEntity.ok(responseToClient)
    }

    /**
     * Influencer의 팔로잉 목록 조회
     *
     * GET /api/v1/follow/following/{influencerId}
     *
     * @param influencerId Influencer ID
     * @return GetFollowingResponseToClient
     */
    @GetMapping("/following/{influencerId}")
    suspend fun getFollowingByInfluencerId(
        @PathVariable influencerId: UUID
    ): ResponseEntity<GetFollowingResponseToClient> {
        logger.info { "GET /api/v1/follow/following/$influencerId" }

        val result = followApiService.getFollowingByInfluencerId(influencerId)

        val responseToClient = GetFollowingResponseToClient(
            frontErrorCode = FrontErrorCode.OK.code,
            errorMessage = FrontErrorCode.OK.message,
            result = result
        )

        logger.info { "Successfully retrieved ${result?.following?.size ?: 0} following for influencer" }
        return ResponseEntity.ok(responseToClient)
    }

    /**
     * Health Check 엔드포인트
     *
     * GET /api/v1/follow/health
     */
    @GetMapping("/health")
    fun health(): ResponseEntity<Map<String, String>> {
        return ResponseEntity.ok(
            mapOf(
                "status" to "UP",
                "service" to "follow-api-controller"
            )
        )
    }
}
