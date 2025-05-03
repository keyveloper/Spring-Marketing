package org.example.marketing.controller

import org.example.marketing.domain.user.AdvertiserPrincipal
import org.example.marketing.dto.board.response.DeleteAdDraftResponse
import org.example.marketing.dto.board.response.IssueNewAdvertisementDraftResponse
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.service.AdvertisementDraftService
import org.example.marketing.service.AdvertisementDslService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
class AdvertisementDraftController(
    private val advertisementDraftService: AdvertisementDraftService,
    private val advertisementDslService: AdvertisementDslService,
) {

    @PostMapping("/advertisement/new-draft")
    fun issue(
        @AuthenticationPrincipal advertiserPrincipal: AdvertiserPrincipal
    ): ResponseEntity<IssueNewAdvertisementDraftResponse> {
        return ResponseEntity.ok().body(
            IssueNewAdvertisementDraftResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                advertisementDraft = advertisementDraftService.issueDraft(advertiserPrincipal.userId)
            )
        )
    }

    @DeleteMapping("/advertisement/draft/{draftId}")
    fun deleteById(
        @PathVariable("draftId") draftId: Long,
        @AuthenticationPrincipal advertiserPrincipal: AdvertiserPrincipal
    ): ResponseEntity<DeleteAdDraftResponse> {
        advertisementDslService.withdrawDraft(
            advertiserId = advertiserPrincipal.userId,
            targetDaftId = draftId,
        )
        return ResponseEntity.ok().body(
            DeleteAdDraftResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
            )
        )
    }
}