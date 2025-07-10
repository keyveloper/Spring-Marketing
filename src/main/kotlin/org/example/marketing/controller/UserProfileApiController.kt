package org.example.marketing.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.dto.user.request.UploadAdvertiserProfileImageRequestFromClient
import org.example.marketing.dto.user.request.UploadInfluencerProfileImageRequestFromClient
import org.example.marketing.dto.user.response.UploadAdvertiserProfileImageResponseToClient
import org.example.marketing.dto.user.response.UploadInfluencerProfileImageResponseToClient
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.enums.UserType
import org.example.marketing.exception.IllegalResourceUsageException
import org.example.marketing.service.AuthApiService
import org.example.marketing.service.UserProfileApiService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class UserProfileApiController(
    private val userProfileApiService: UserProfileApiService,
    private val authApiService: AuthApiService,
) {
    private val logger = KotlinLogging.logger {}

    @PostMapping("/profile/image/advertiser")
    suspend fun uploadAdvertiserProfileImage(
        @RequestHeader("Authorization") authorization: String,
        @RequestPart("draftId") meta: UploadAdvertiserProfileImageRequestFromClient,
        @RequestPart("file") file: MultipartFile
    ): ResponseEntity<UploadAdvertiserProfileImageResponseToClient> {
        logger.info { "Validating user authorization for profile image upload" }
        logger.info { "Received Authorization header: ${authorization.take(50)}..." }

        val extractedUser = authApiService.validateAdvertiser(authorization)
        val userId = extractedUser.userId
        val userType = extractedUser.userType ?: throw IllegalResourceUsageException(
            message = "uploadProfile request must include userType!",
            logics = "UserProfileApiController.uploadAdvertiserProfileImage"
        )

        if (userType.uppercase().startsWith("ADVERTISER_")) throw IllegalResourceUsageException(
            message = "uploadAdvertiserProfile request must include userType!",
            logics = "UserProfileApiController.uploadAdvertiserProfileImage"
        )


        val result = userProfileApiService.uploadAdvertiserProfileImageToApiServer(
            userId,
            userType,
            meta.advertiserProfileDraftId,
            file
        )

        return ResponseEntity.ok().body(
            UploadAdvertiserProfileImageResponseToClient.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                result = result
            )
        )
    }

    @PostMapping("/profile/image/influencer")
    suspend fun uploadInfluencerProfileImage(
        @RequestHeader("Authorization") authorization: String,
        @RequestPart("draftId") meta: UploadInfluencerProfileImageRequestFromClient,
        @RequestPart("file") file: MultipartFile
    ): ResponseEntity<UploadInfluencerProfileImageResponseToClient> {
        logger.info { "Validating user authorization for profile image upload" }
        logger.info { "Received Authorization header: ${authorization.take(50)}..." }

        val extractedUser = authApiService.validateAdvertiser(authorization)
        val userId = extractedUser.userId
        val userType = extractedUser.userType ?: throw IllegalResourceUsageException(
            message = "uploadProfile request must include userType!",
            logics = "UserProfileApiController.uploadInfluencerProfileImage"
        )

        if (userType.uppercase() != UserType.INFLUENCER.name) throw IllegalResourceUsageException(
            message = "you are not a influencer",
            logics = "UserProfileApiController.uploadInfluencerProfileImage"
        )


        val result = userProfileApiService.uploadInfluencerProfileImageToApiServer(
            userId,
            userType,
            meta.influencerProfileDraftId,
            file
        )

        return ResponseEntity.ok().body(
            UploadInfluencerProfileImageResponseToClient.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                result = result
            )
        )
    }
}
