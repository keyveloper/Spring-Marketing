package org.example.marketing.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.dto.board.response.DeleteAdDraftResponse
import org.example.marketing.dto.board.response.GetDraftResponse
import org.example.marketing.dto.board.response.IssueNewAdvertisementDraftResponse
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.service.AdvertisementDraftService
import org.example.marketing.service.AuthApiService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

private val logger = KotlinLogging.logger {}

@RestController
class AdvertisementDraftController(
    private val advertisementDraftService: AdvertisementDraftService,
    private val authApiService: AuthApiService,
) {

    @PostMapping("/advertisement/new-draft")
    suspend fun issue(
        @RequestHeader("Authorization") authorization: String,
    ): ResponseEntity<IssueNewAdvertisementDraftResponse> {
        logger.info { "Validating advertiser authorization for advertisement creation" }
        val extractedAdvertiser = authApiService.validateAdvertiser(authorization)
        logger.info { "Advertiser validated: userId=${extractedAdvertiser.userId}, email=${extractedAdvertiser.email}" }
        val extractedAdvertiserId = extractedAdvertiser.userId

        return ResponseEntity.ok().body(
            IssueNewAdvertisementDraftResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                advertisementDraft = advertisementDraftService.issueDraft(extractedAdvertiserId)
            )
        )
    }


    @GetMapping("/advertisement/draft/{draftId}")
    fun getDraftById(
        @PathVariable("draftId") draftId: Long
    ): ResponseEntity<GetDraftResponse> {
        return ResponseEntity.ok().body(
            GetDraftResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                advertisementDraft = advertisementDraftService.findById(draftId)
            )
        )
    }
}
