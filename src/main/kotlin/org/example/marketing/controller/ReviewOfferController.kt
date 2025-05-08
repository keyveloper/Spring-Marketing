package org.example.marketing.controller

import jakarta.validation.Valid
import org.example.marketing.domain.user.InfluencerPrincipal
import org.example.marketing.dto.functions.request.NewOfferReviewRequest
import org.example.marketing.dto.functions.response.GetOfferingInfluencerInfosResponse
import org.example.marketing.dto.functions.response.GetValidReviewOfferAdResponse
import org.example.marketing.dto.functions.response.NewOfferReviewResponse
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.service.ReviewOfferService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
class ReviewOfferController(
    private val reviewOfferService: ReviewOfferService
) {

    @PostMapping("/review-offer")
    fun offer(
        @Valid @RequestBody request: NewOfferReviewRequest,
        @AuthenticationPrincipal influencerPrincipal: InfluencerPrincipal
    ): ResponseEntity<NewOfferReviewResponse> {
        return ResponseEntity.ok().body(
            NewOfferReviewResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                createdEntityId = reviewOfferService.save(request, influencerPrincipal.userId)
            )
        )
    }

    @GetMapping("/open/review-offers/{advertisementId}")
    fun getOfferingInfluencerInfos(
        @PathVariable("advertisementId") advertisementId: Long
    ):  ResponseEntity<GetOfferingInfluencerInfosResponse> {
        return ResponseEntity.ok().body(
            GetOfferingInfluencerInfosResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                infos = reviewOfferService.findOfferingInfluencerInfos(advertisementId)
            )
        )
    }

    @GetMapping("/review-offers/influencer-valid")
    fun getValidOfferByInfluencerId(
        @AuthenticationPrincipal influencerPrincipal: InfluencerPrincipal
    ): ResponseEntity<GetValidReviewOfferAdResponse> {
        return ResponseEntity.ok().body(
            GetValidReviewOfferAdResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                result = reviewOfferService.findAllValidAdsByInfluencerId(influencerPrincipal.userId)
            )
        )
    }
}