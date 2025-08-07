package org.example.marketing.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.dto.follow.response.GetFollowerInfluencerUserSummaryResponseToClient
import org.example.marketing.dto.follow.response.GetFollowingAdvertiserUserSummaryResponseToClient
import org.example.marketing.dto.user.response.GetAdvertiserProfileSummarizedResponseToClient
import java.util.UUID
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.service.ProfileSummaryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/summary")
class SummaryProfileController(
    private val rProfileSummaryService: ProfileSummaryService
) {
    private val logger = KotlinLogging.logger {}

    @GetMapping("/advertisement-owner/{advertisementId}")
    suspend fun getAdvertiserProfileByAdvertisementId(
        @PathVariable advertisementId: Long
    ): ResponseEntity<GetAdvertiserProfileSummarizedResponseToClient> {
        logger.info { "Getting advertiser profile summary for advertisementId: $advertisementId" }

        val result = rProfileSummaryService.getAdvertiserProfileByAdvertisementId(advertisementId)

        return ResponseEntity.ok().body(
            GetAdvertiserProfileSummarizedResponseToClient.of(
                result = result,
                frontErrorCode = FrontErrorCode.OK
            )
        )
    }

    @GetMapping("/following/{influencerId}")
    suspend fun getFollowingAdvertiserUserSummary(
        @PathVariable influencerId: UUID
    ): ResponseEntity<GetFollowingAdvertiserUserSummaryResponseToClient> {
        logger.info { "Getting following advertiser summary for influencerId: $influencerId" }

        val result = rProfileSummaryService.getFollowingAdvertiserUserSummary(influencerId)

        return ResponseEntity.ok().body(
            GetFollowingAdvertiserUserSummaryResponseToClient.of(
                result = result,
                frontErrorCode = FrontErrorCode.OK
            )
        )
    }

    @GetMapping("/followers/{advertiserId}")
    suspend fun getFollowerInfluencerUserSummary(
        @PathVariable advertiserId: UUID
    ): ResponseEntity<GetFollowerInfluencerUserSummaryResponseToClient> {
        logger.info { "Getting follower influencer summary for advertiserId: $advertiserId" }

        val result = rProfileSummaryService.getFollowerInfluencerUserSummary(advertiserId)

        return ResponseEntity.ok().body(
            GetFollowerInfluencerUserSummaryResponseToClient.of(
                result = result,
                frontErrorCode = FrontErrorCode.OK
            )
        )
    }
}
