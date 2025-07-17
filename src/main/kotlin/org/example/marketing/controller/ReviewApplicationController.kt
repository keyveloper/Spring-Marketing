package org.example.marketing.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.validation.Valid
import org.example.marketing.dto.board.request.ApplyReviewRequest
import org.example.marketing.dto.board.response.ApplyReviewResponse
import org.example.marketing.dto.board.response.GetReviewApplicationsByAdvertisementIdResponse
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.enums.UserType
import org.example.marketing.exception.IllegalResourceUsageException
import org.example.marketing.service.AuthApiService
import org.example.marketing.service.ReviewApplicationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class ReviewApplicationController(
    private val reviewApplicationService: ReviewApplicationService,
    private val authApiService: AuthApiService
) {
    private val logger = KotlinLogging.logger {}

    @PostMapping("/review-application")
    suspend fun applyReview(
        @RequestHeader("Authorization") authorization: String,
        @Valid @RequestBody request: ApplyReviewRequest
    ): ResponseEntity<ApplyReviewResponse> {
        logger.info { "Validating influencer authorization for review application" }
        logger.info { "Received Authorization header: ${authorization.take(50)}..." }

        val extractedUser = authApiService.validateInfluencer(authorization)
        val influencerId = extractedUser.userId
        val influencerUsername = extractedUser.name
            ?: throw IllegalResourceUsageException(
                message = "Influencer name is required for review application",
                logics = "ReviewApplicationController.applyReview"
            )
        val influencerEmail = extractedUser.email
            ?: throw IllegalResourceUsageException(
                message = "Influencer email is required for review application",
                logics = "ReviewApplicationController.applyReview"
            )
        val influencerMobile = extractedUser.phoneNumber
            ?: throw IllegalResourceUsageException(
                message = "Influencer mobile is required for review application",
                logics = "ReviewApplicationController.applyReview"
            )

        if (extractedUser.userType != UserType.INFLUENCER) {
            throw IllegalResourceUsageException(
                message = "Review application must be called by influencer user",
                logics = "ReviewApplicationController.applyReview"
            )
        }

        val createdId = reviewApplicationService.save(
            request = request,
            influencerId = influencerId,
            influencerUsername = influencerUsername,
            influencerEmail = influencerEmail,
            influencerMobile = influencerMobile
        )

        return ResponseEntity.ok().body(
            ApplyReviewResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                createdApplicationId = createdId
            )
        )
    }

    @GetMapping("/open/review-applications/{advertisementId}")
    fun getReviewApplicationsByAdvertisementId(
        @PathVariable("advertisementId") advertisementId: Long
    ): ResponseEntity<GetReviewApplicationsByAdvertisementIdResponse> {
        logger.info { "Getting review applications for advertisementId: $advertisementId" }

        val applications = reviewApplicationService.findByAdvertisementId(advertisementId)

        return ResponseEntity.ok().body(
            GetReviewApplicationsByAdvertisementIdResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                applications = applications
            )
        )
    }
}
