package org.example.marketing.controller

import org.example.marketing.domain.user.AdvertiserPrincipal
import org.example.marketing.dto.board.response.DeleteAdDraftResponse
import org.example.marketing.dto.board.response.GetDraftResponse
import org.example.marketing.dto.board.response.IssueNewAdvertisementDraftResponse
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.service.AdvertisementDraftService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
class AdvertisementDraftController(
    private val advertisementDraftService: AdvertisementDraftService,
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
