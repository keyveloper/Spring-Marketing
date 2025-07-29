package org.example.marketing.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.dto.user.request.UploadInfluencerProfileInfoRequestFromClient
import org.example.marketing.dto.user.response.UploadInfluencerProfileInfoResponseToClient
import org.example.marketing.dto.user.response.UploadInfluencerProfileInfoResult
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.enums.UserType
import org.example.marketing.exception.IllegalResourceUsageException
import org.example.marketing.service.AuthApiService
import org.example.marketing.service.UserProfileApiService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class InfluencerProfileApiController(
    val authApiService: AuthApiService,
    val userProfileApiService: UserProfileApiService
) {
    private val logger = KotlinLogging.logger {}
    @PostMapping("/profile/info/influencer")
    suspend fun uploadInfluencerProfileInfo(
        @RequestHeader("Authorization") authorization: String,
        @RequestBody request: UploadInfluencerProfileInfoRequestFromClient
    ): ResponseEntity<UploadInfluencerProfileInfoResponseToClient> {
        logger.info { "Validating user authorization for influencer profile info upload" }
        logger.info { "Received Authorization header: ${authorization.take(50)}..." }

        val extractedUser = authApiService.validateInfluencer(authorization)
        val influencerId = extractedUser.userId
        val userType = extractedUser.userType

        if (userType != UserType.INFLUENCER) throw IllegalResourceUsageException(
            message = "you are not an influencer",
            logics = "UserProfileController.uploadInfluencerProfileInfo"
        )

        val result = userProfileApiService.uploadInfluencerProfileInfoToApiServer(
            influencerId = influencerId,
            userType = userType,
            userProfileDraftId = request.userProfileDraftId,
            introduction = request.introduction,
            job = request.job
        )

        return ResponseEntity.ok().body(
            UploadInfluencerProfileInfoResponseToClient.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                result = UploadInfluencerProfileInfoResult(savedId = result.savedId)
            )
        )
    }
}