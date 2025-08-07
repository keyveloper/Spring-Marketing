package org.example.marketing.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.dto.follow.request.FollowRequestFromClient
import org.example.marketing.dto.follow.request.UnFollowRequestFromClient
import org.example.marketing.dto.follow.response.FollowResponseToClient
import org.example.marketing.dto.follow.response.UnFollowResponseToClient
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.service.AuthApiService
import org.example.marketing.service.FollowApiService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Follow API Controller
 * 팔로우/언팔로우 및 팔로워/팔로잉 조회 엔드포인트를 제공합니다.
 */
@RestController
@RequestMapping()
class FollowApiController(
    private val authApiService: AuthApiService,
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
    @PostMapping("/follow")
    suspend fun follow(
        @RequestHeader("Authorization") authorization: String,
        @RequestBody requestFromClient: FollowRequestFromClient
    ): ResponseEntity<FollowResponseToClient> {
        val extractedInfluencer = authApiService.validateInfluencer(authorization)
        val influencerId = extractedInfluencer.userId
        logger.info { "POST /api/v1/follow - advertiserId=${requestFromClient.advertiserId}, influencerId=${influencerId}" }

        val result = followApiService.follow(
            advertiserId = requestFromClient.advertiserId,
            influencerId = extractedInfluencer.userId
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
        @RequestHeader("Authorization") authorization: String,
        @RequestBody requestFromClient: UnFollowRequestFromClient
    ): ResponseEntity<UnFollowResponseToClient> {
        val extractedInfluencer = authApiService.validateInfluencer(authorization)
        val influencerId = extractedInfluencer.userId
        logger.info { "POST /api/v1/follow/unfollow - advertiserId=${requestFromClient.advertiserId}" +
                ", influencerId=${influencerId}" }

        val result = followApiService.unFollow(
            advertiserId = requestFromClient.advertiserId,
            influencerId = influencerId
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
