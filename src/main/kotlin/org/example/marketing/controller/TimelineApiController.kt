package org.example.marketing.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.dto.timeline.request.DeleteTimelineAdRequestFromClient
import org.example.marketing.dto.timeline.request.GetTimelineAdsRequestFromClient
import org.example.marketing.dto.timeline.request.UploadTimelineAdRequestFromClient
import org.example.marketing.dto.timeline.response.DeleteTimelineAdResponseToClient
import org.example.marketing.dto.timeline.response.GetTimelineAdsResponseToClient
import org.example.marketing.dto.timeline.response.UploadTimelineAdResponseToClient
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.service.TimelineApiService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Timeline API Controller
 * 타임라인 광고 조회, 추가, 삭제 엔드포인트를 제공합니다.
 */
@RestController
@RequestMapping("/api/v1/timeline")
class TimelineApiController(
    private val timelineApiService: TimelineApiService
) {
    private val logger = KotlinLogging.logger {}

    /**
     * 타임라인 광고 목록 조회
     *
     * GET /api/v1/timeline/ads
     *
     * @param requestFromClient GetTimelineAdsRequestFromClient
     * @return GetTimelineAdsResponseToClient
     */
    @GetMapping("/ads")
    suspend fun getTimelineAds(
        @ModelAttribute requestFromClient: GetTimelineAdsRequestFromClient
    ): ResponseEntity<GetTimelineAdsResponseToClient> {
        logger.info { "GET /api/v1/timeline/ads - influencerId=${requestFromClient.influencerId}, cursor=${requestFromClient.cursor}" }

        val result = timelineApiService.getTimelineAds(
            influencerId = requestFromClient.influencerId,
            cursor = requestFromClient.cursor,
            pivotTime = requestFromClient.pivotTime
        )

        val responseToClient = GetTimelineAdsResponseToClient(
            frontErrorCode = FrontErrorCode.OK.code,
            errorMessage = FrontErrorCode.OK.message,
            result = result
        )

        logger.info { "Successfully retrieved ${result.timelineAds.size} timeline ads" }
        return ResponseEntity.ok(responseToClient)
    }

    /**
     * 타임라인에 광고 추가
     *
     * POST /api/v1/timeline/ads
     *
     * @param requestFromClient UploadTimelineAdRequestFromClient
     * @return UploadTimelineAdResponseToClient
     */
    @PostMapping("/ads")
    suspend fun uploadTimelineAd(
        @RequestBody requestFromClient: UploadTimelineAdRequestFromClient
    ): ResponseEntity<UploadTimelineAdResponseToClient> {
        logger.info { "POST /api/v1/timeline/ads - influencerId=${requestFromClient.influencerId}, advertisementId=${requestFromClient.advertisementId}" }

        val result = timelineApiService.uploadTimelineAd(
            influencerId = requestFromClient.influencerId,
            advertisementId = requestFromClient.advertisementId
        )

        val responseToClient = UploadTimelineAdResponseToClient(
            frontErrorCode = FrontErrorCode.OK.code,
            errorMessage = FrontErrorCode.OK.message,
            result = result
        )

        logger.info { "Successfully uploaded timeline ad: advertisementId=${requestFromClient.advertisementId}" }
        return ResponseEntity.ok(responseToClient)
    }

    /**
     * 타임라인에서 광고 삭제
     *
     * DELETE /api/v1/timeline/ads
     *
     * @param requestFromClient DeleteTimelineAdRequestFromClient
     * @return DeleteTimelineAdResponseToClient
     */
    @PostMapping("/delete/ads")
    suspend fun deleteTimelineAd(
        @RequestBody requestFromClient: DeleteTimelineAdRequestFromClient
    ): ResponseEntity<DeleteTimelineAdResponseToClient> {
        logger.info { "DELETE /api/v1/timeline/ads -" +
                " influencerId=${requestFromClient.influencerId}, " +
                "advertisementId=${requestFromClient.advertisementId}" }

        val deletedRow = timelineApiService.deleteTimelineAd(
            influencerId = requestFromClient.influencerId,
            advertisementId = requestFromClient.advertisementId
        )

        val responseToClient = DeleteTimelineAdResponseToClient(
            frontErrorCode = FrontErrorCode.OK.code,
            errorMessage = FrontErrorCode.OK.message,
            deletedRow = deletedRow
        )

        logger.info { "Successfully deleted timeline ad: deletedRow=$deletedRow" }
        return ResponseEntity.ok(responseToClient)
    }

    /**
     * Health Check 엔드포인트
     *
     * GET /api/v1/timeline/health
     */
    @GetMapping("/health")
    fun health(): ResponseEntity<Map<String, String>> {
        return ResponseEntity.ok(
            mapOf(
                "status" to "UP",
                "service" to "timeline-api-controller"
            )
        )
    }
}
