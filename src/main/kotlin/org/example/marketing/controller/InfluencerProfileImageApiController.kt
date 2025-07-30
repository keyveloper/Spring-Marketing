package org.example.marketing.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.dto.user.request.UploadInfluencerProfileImageRequestFromClient
import org.example.marketing.dto.user.response.UploadInfluencerProfileImageResponseToClient
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.enums.UserType
import org.example.marketing.exception.IllegalResourceUsageException
import org.example.marketing.service.AuthApiService
import org.example.marketing.service.InfluencerProfileImageApiService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
class InfluencerProfileImageApiController(
    private val influencerProfileImageApiService: InfluencerProfileImageApiService,
    private val authApiService: AuthApiService,
) {
    private val logger = KotlinLogging.logger {}

    @PostMapping("/profile/image/influencer")
    suspend fun uploadInfluencerProfileImage(
        @RequestHeader("Authorization") authorization: String,
        @RequestPart("meta") meta: UploadInfluencerProfileImageRequestFromClient,
        @RequestPart("file") file: MultipartFile
    ): ResponseEntity<UploadInfluencerProfileImageResponseToClient> {
        logger.info { "Validating user authorization for influencer profile image upload" }
        logger.info { "Received Authorization header: ${authorization.take(50)}..." }

        val extractedUser = authApiService.validateInfluencer(authorization)
        val userId = extractedUser.userId
        val userType = extractedUser.userType

        if (userType != UserType.INFLUENCER) throw IllegalResourceUsageException(
            message = "you are not an influencer",
            logics = "InfluencerProfileImageApiController.uploadInfluencerProfileImage"
        )

        val result = influencerProfileImageApiService.uploadProfileImageToImageServer(
            userId,
            userType,
            meta.profileImageType,
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
