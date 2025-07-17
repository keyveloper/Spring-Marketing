package org.example.marketing.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.dto.user.response.GetUserProfileDraftResponse
import org.example.marketing.dto.user.response.IssueNewUserProfileDraftResponse
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.enums.UserType
import org.example.marketing.service.AuthApiService
import org.example.marketing.service.UserProfileDraftService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

private val logger = KotlinLogging.logger {}

@RestController
class UserProfileDraftController(
    private val userProfileDraftService: UserProfileDraftService,
    private val authApiService: AuthApiService,
) {

    @GetMapping("/influencer/profile/new-draft")
    suspend fun issueInfluencerDraft(
        @RequestHeader("Authorization") authorization: String,
    ): ResponseEntity<IssueNewUserProfileDraftResponse> {
        logger.info { "Validating influencer authorization for profile draft creation" }
        val extractedInfluencer = authApiService.validateInfluencer(authorization)
        logger.info { "Influencer validated: userId=${extractedInfluencer.userId}, email=${extractedInfluencer.email}" }
        val extractedInfluencerId = extractedInfluencer.userId

        return ResponseEntity.ok().body(
            IssueNewUserProfileDraftResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                userProfileDraft = userProfileDraftService.issueDraft(extractedInfluencerId, UserType.INFLUENCER)
            )
        )
    }

    @GetMapping("/advertiser/profile/new-draft")
    suspend fun issueAdvertiserDraft(
        @RequestHeader("Authorization") authorization: String,
    ): ResponseEntity<IssueNewUserProfileDraftResponse> {
        logger.info { "Validating advertiser authorization for profile draft creation" }
        val extractedAdvertiser = authApiService.validateAdvertiser(authorization)
        logger.info { "Advertiser validated: userId=${extractedAdvertiser.userId}, email=${extractedAdvertiser.email}" }
        val extractedAdvertiserId = extractedAdvertiser.userId

        return ResponseEntity.ok().body(
            IssueNewUserProfileDraftResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                userProfileDraft = userProfileDraftService.issueDraft(extractedAdvertiserId, UserType.ADVERTISER_COMMON)
            )
        )
    }

    @GetMapping("/user/profile/draft/{draftId}")
    fun getDraftById(
        @PathVariable("draftId") draftId: UUID
    ): ResponseEntity<GetUserProfileDraftResponse> {
        return ResponseEntity.ok().body(
            GetUserProfileDraftResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                userProfileDraft = userProfileDraftService.findById(draftId)
            )
        )
    }
}