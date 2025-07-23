package org.example.marketing.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.dto.like.request.LikeOrSwitchRequestFromClient
import org.example.marketing.dto.like.response.LikeOrSwitchResponseToClient
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.service.LikeApiService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Like API Controller
 * 좋아요/좋아요 취소 엔드포인트를 제공합니다.
 */
@RestController
@RequestMapping("/api/v1/like")
class LikeApiController(
    private val likeApiService: LikeApiService
) {
    private val logger = KotlinLogging.logger {}

    /**
     * 좋아요 또는 좋아요 취소 토글
     *
     * POST /api/v1/like/ad
     *
     * @param requestFromClient LikeOrSwitchRequestFromClient
     * @return LikeOrSwitchResponseToClient
     */
    @PostMapping("/ad")
    suspend fun likeOrSwitch(
        @RequestBody requestFromClient: LikeOrSwitchRequestFromClient
    ): ResponseEntity<LikeOrSwitchResponseToClient> {
        logger.info { "POST /api/v1/like/ad - influencerId=${requestFromClient.influencerId}, advertisementId=${requestFromClient.advertisementId}" }

        val result = likeApiService.likeOrSwitch(
            influencerId = requestFromClient.influencerId,
            advertisementId = requestFromClient.advertisementId
        )

        val responseToClient = LikeOrSwitchResponseToClient(
            frontErrorCode = FrontErrorCode.OK.code,
            errorMessage = FrontErrorCode.OK.message,
            result = result
        )

        logger.info { "Successfully processed like/switch: status=${result.likeStatus}" }
        return ResponseEntity.ok(responseToClient)
    }

    /**
     * Health Check 엔드포인트
     *
     * GET /api/v1/like/health
     */
    @GetMapping("/health")
    fun health(): ResponseEntity<Map<String, String>> {
        return ResponseEntity.ok(
            mapOf(
                "status" to "UP",
                "service" to "like-api-controller"
            )
        )
    }
}
