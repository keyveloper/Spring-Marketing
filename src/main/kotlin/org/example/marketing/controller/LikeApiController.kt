package org.example.marketing.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.dto.like.request.LikeRequestFromClient
import org.example.marketing.dto.like.request.UnLikeRequestFromClient
import org.example.marketing.dto.like.response.*
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.service.AuthApiService
import org.example.marketing.service.LikeApiService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

/**
 * Like API Controller
 * 좋아요/좋아요 취소 엔드포인트를 제공합니다.
 */
@RestController
@RequestMapping("/api/v1/like")
class LikeApiController(
    private val likeApiService: LikeApiService,
    private val authApiService: AuthApiService
) {
    private val logger = KotlinLogging.logger {}

    /**
     * 좋아요
     *
     * POST /api/v1/like/ad
     *
     * @param authorization JWT 토큰
     * @param requestFromClient LikeRequestFromClient
     * @return LikeResponseToClient
     */
    @PostMapping("/ad")
    suspend fun like(
        @RequestHeader("Authorization") authorization: String,
        @RequestBody requestFromClient: LikeRequestFromClient
    ): ResponseEntity<LikeResponseToClient> {
        val extractedUser = authApiService.validateInfluencer(authorization)
        logger.info { "POST /api/v1/like/ad - influencerId=${extractedUser.userId}, advertisementId=${requestFromClient.advertisementId}" }

        val result = likeApiService.like(
            influencerId = extractedUser.userId,
            advertisementId = requestFromClient.advertisementId
        )

        val responseToClient = LikeResponseToClient(
            frontErrorCode = FrontErrorCode.OK.code,
            errorMessage = FrontErrorCode.OK.message,
            result = result
        )

        return ResponseEntity.ok(responseToClient)
    }

    /**
     * 좋아요 취소
     *
     * POST /api/v1/like/ad/unlike
     *
     * @param authorization JWT 토큰
     * @param requestFromClient UnLikeRequestFromClient
     * @return UnLikeResponseToClient
     */
    @PostMapping("/ad/unlike")
    suspend fun unLike(
        @RequestHeader("Authorization") authorization: String,
        @RequestBody requestFromClient: UnLikeRequestFromClient
    ): ResponseEntity<UnLikeResponseToClient> {
        val extractedUser = authApiService.validateInfluencer(authorization)
        logger.info { "POST /api/v1/like/ad/unlike - influencerId=${extractedUser.userId}, advertisementId=${requestFromClient.advertisementId}" }

        val result = likeApiService.unLike(
            influencerId = extractedUser.userId,
            advertisementId = requestFromClient.advertisementId
        )

        val responseToClient = UnLikeResponseToClient(
            frontErrorCode = FrontErrorCode.OK.code,
            errorMessage = FrontErrorCode.OK.message,
            result = result
        )

        logger.info { "Successfully unliked: effectedRow=${result.effectedRow}" }
        return ResponseEntity.ok(responseToClient)
    }

    /**
     * 인플루언서가 좋아요한 광고 목록 조회
     *
     * GET /api/v1/like/influencer/{influencerId}
     *
     * @param influencerId Influencer ID
     * @return GetLikedAdsByInfluencerIdResponseToClient
     */
    @GetMapping("/influencer/{influencerId}")
    suspend fun getLikedAdsByInfluencerId(
        @PathVariable influencerId: UUID
    ): ResponseEntity<GetLikedAdsByInfluencerIdResponseToClient> {
        logger.info { "GET /api/v1/like/influencer/$influencerId" }

        val result = likeApiService.getLikedAdsByInfluencerId(influencerId)

        val responseToClient = GetLikedAdsByInfluencerIdResponseToClient(
            frontErrorCode = FrontErrorCode.OK.code,
            errorMessage = FrontErrorCode.OK.message,
            result = result
        )

        logger.info { "Successfully got liked ads: count=${result.likedAdvertisements.size}" }
        return ResponseEntity.ok(responseToClient)
    }

    /**
     * 광고를 좋아요한 인플루언서 목록 조회
     *
     * GET /api/v1/like/advertisement/{advertisementId}
     *
     * @param advertisementId Advertisement ID
     * @return GetInfluencersByAdIdResponseToClient
     */
    @GetMapping("/advertisement/{advertisementId}")
    suspend fun getInfluencersByAdId(
        @PathVariable advertisementId: Long
    ): ResponseEntity<GetInfluencersByAdIdResponseToClient> {
        logger.info { "GET /api/v1/like/advertisement/$advertisementId" }

        val result = likeApiService.getInfluencersByAdId(advertisementId)

        val responseToClient = GetInfluencersByAdIdResponseToClient(
            frontErrorCode = FrontErrorCode.OK.code,
            errorMessage = FrontErrorCode.OK.message,
            result = result
        )

        logger.info { "Successfully got influencers: count=${result.influencerIds.size}" }
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
