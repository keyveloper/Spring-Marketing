package org.example.marketing.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.dto.user.request.UploadAdvertiserProfileInfoRequestFromClient
import org.example.marketing.dto.user.response.UploadAdvertiserProfileInfoResponseToClient
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.exception.IllegalResourceUsageException
import org.example.marketing.service.AuthApiService
import org.example.marketing.service.UserProfileApiService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class AdvertiserProfileController(
    private val authApiService: AuthApiService,
    private val userProfileApiService: UserProfileApiService,
) {
    private val logger = KotlinLogging.logger {}
    @PostMapping("/profile/info/advertiser")
    suspend fun uploadAdvertiserProfileInfo(
        @RequestHeader("Authorization") authorization: String,
        @RequestBody request: UploadAdvertiserProfileInfoRequestFromClient
    ): ResponseEntity<UploadAdvertiserProfileInfoResponseToClient> {
        logger.info { "Validating user authorization for advertiser profile info upload" }
        logger.info { "Received Authorization header: ${authorization.take(50)}..." }

        val extractedUser = authApiService.validateAdvertiser(authorization)
        val advertiserId = extractedUser.userId
        val userType = extractedUser.userType

        if (!userType.name.startsWith("ADVERTISER_")) throw IllegalResourceUsageException(
            message = "uploadAdvertiserProfileInfo request must be called by advertiser user!",
            logics = "UserProfileController.uploadAdvertiserProfileInfo"
        )

        val result = userProfileApiService.uploadAdvertiserProfileInfoToApiServer(
            advertiserId = advertiserId,
            userProfileDraftId = request.userProfileDraftId,
            serviceInfo = request.serviceInfo,
            locationBrief = request.locationBrief,
            introduction = request.introduction,
            userType = userType
        )

        return ResponseEntity.ok().body(
            UploadAdvertiserProfileInfoResponseToClient.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                result = result.changedRows
            )
        )
    }
}