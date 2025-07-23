package org.example.marketing.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.dto.noti.request.UploadNotiToAdvertiserApiRequest
import org.example.marketing.dto.noti.request.UploadNotiToAdvertiserRequestFromClient
import org.example.marketing.dto.noti.request.UploadNotiToInfluencerApiRequest
import org.example.marketing.dto.noti.request.UploadNotiToInfluencerRequestFromClient
import org.example.marketing.dto.noti.response.*
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.service.NotiApiService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

/**
 * Noti API Controller
 * 알림 생성 및 조회 엔드포인트를 제공합니다.
 */
@RestController
@RequestMapping("/api/v1/noti")
class NotiApiController(
    private val notiApiService: NotiApiService
) {
    private val logger = KotlinLogging.logger {}

    /**
     * Advertiser에게 알림 생성
     *
     * POST /api/v1/noti/to-advertiser
     *
     * @param requestFromClient UploadNotiToAdvertiserRequestFromClient
     * @return UploadNotiToAdvertiserResponseToClient
     */
    @PostMapping("/to-advertiser")
    suspend fun createNotiToAdvertiser(
        @RequestBody requestFromClient: UploadNotiToAdvertiserRequestFromClient
    ): ResponseEntity<UploadNotiToAdvertiserResponseToClient> {
        logger.info { "POST /api/v1/noti/to-advertiser - advertiserId=${requestFromClient.advertiserId}, type=${requestFromClient.notiToAdvertiserType}" }

        val apiRequest = UploadNotiToAdvertiserApiRequest(
            message = requestFromClient.message,
            advertiserId = requestFromClient.advertiserId,
            notiToAdvertiserType = requestFromClient.notiToAdvertiserType
        )

        val result = notiApiService.createNotiToAdvertiser(apiRequest)

        val responseToClient = UploadNotiToAdvertiserResponseToClient(
            frontErrorCode = FrontErrorCode.OK.code,
            errorMessage = FrontErrorCode.OK.message,
            result = result
        )

        logger.info { "Successfully created notification to advertiser: notiId=${result.notiId}" }
        return ResponseEntity.ok(responseToClient)
    }

    /**
     * Influencer에게 알림 생성
     *
     * POST /api/v1/noti/to-influencer
     *
     * @param requestFromClient UploadNotiToInfluencerRequestFromClient
     * @return UploadNotiToInfluencerResponseToClient
     */
    @PostMapping("/to-influencer")
    suspend fun createNotiToInfluencer(
        @RequestBody requestFromClient: UploadNotiToInfluencerRequestFromClient
    ): ResponseEntity<UploadNotiToInfluencerResponseToClient> {
        logger.info { "POST /api/v1/noti/to-influencer - influencerId=${requestFromClient.influencerId}, type=${requestFromClient.notiToInfluencerType}" }

        val apiRequest = UploadNotiToInfluencerApiRequest(
            message = requestFromClient.message,
            influencerId = requestFromClient.influencerId,
            notiToInfluencerType = requestFromClient.notiToInfluencerType
        )

        val result = notiApiService.createNotiToInfluencer(apiRequest)

        val responseToClient = UploadNotiToInfluencerResponseToClient(
            frontErrorCode = FrontErrorCode.OK.code,
            errorMessage = FrontErrorCode.OK.message,
            result = result
        )

        logger.info { "Successfully created notification to influencer: notiId=${result.notiId}" }
        return ResponseEntity.ok(responseToClient)
    }

    /**
     * Advertiser의 알림 목록 조회
     *
     * GET /api/v1/noti/to-advertiser/advertiser/{advertiserId}
     *
     * @param advertiserId Advertiser ID
     * @return GetNotiToAdvertisersByAdvertiserIdResponseToClient
     */
    @GetMapping("/to-advertiser/{advertiserId}")
    suspend fun getNotiToAdvertisersByAdvertiserId(
        @PathVariable advertiserId: UUID
    ): ResponseEntity<GetNotiToAdvertisersByAdvertiserIdResponseToClient> {
        logger.info { "GET /api/v1/noti/to-advertiser/advertiser/$advertiserId" }

        val result = notiApiService.getNotiToAdvertisersByAdvertiserId(advertiserId)

        val responseToClient = GetNotiToAdvertisersByAdvertiserIdResponseToClient(
            frontErrorCode = FrontErrorCode.OK.code,
            errorMessage = FrontErrorCode.OK.message,
            result = result
        )

        logger.info { "Successfully retrieved ${result.size} notifications for advertiser" }
        return ResponseEntity.ok(responseToClient)
    }

    /**
     * Influencer의 알림 목록 조회
     *
     * GET /api/v1/noti/to-influencer/influencer/{influencerId}
     *
     * @param influencerId Influencer ID
     * @return GetNotiToInfluencersByInfluencerIdResponseToClient
     */
    @GetMapping("/to-influencer/{influencerId}")
    suspend fun getNotiToInfluencersByInfluencerId(
        @PathVariable influencerId: UUID
    ): ResponseEntity<GetNotiToInfluencersByInfluencerIdResponseToClient> {
        logger.info { "GET /api/v1/noti/to-influencer/influencer/$influencerId" }

        val result = notiApiService.getNotiToInfluencersByInfluencerId(influencerId)

        val responseToClient = GetNotiToInfluencersByInfluencerIdResponseToClient(
            frontErrorCode = FrontErrorCode.OK.code,
            errorMessage = FrontErrorCode.OK.message,
            result = result
        )

        logger.info { "Successfully retrieved ${result.size} notifications for influencer" }
        return ResponseEntity.ok(responseToClient)
    }

    /**
     * Health Check 엔드포인트
     *
     * GET /api/v1/noti/health
     */
    @GetMapping("/health")
    fun health(): ResponseEntity<Map<String, String>> {
        return ResponseEntity.ok(
            mapOf(
                "status" to "UP",
                "service" to "noti-api-controller"
            )
        )
    }
}
