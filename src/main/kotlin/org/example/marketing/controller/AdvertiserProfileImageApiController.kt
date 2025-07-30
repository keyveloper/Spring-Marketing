package org.example.marketing.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.dto.user.request.UploadAdvertiserProfileImageRequestFromClient
import org.example.marketing.dto.user.response.UploadAdvertiserProfileImageResponseToClient
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.exception.IllegalResourceUsageException
import org.example.marketing.service.AdvertiserProfileImageApiService
import org.example.marketing.service.AuthApiService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
class AdvertiserProfileImageApiController(
    private val advertiserProfileImageApiService: AdvertiserProfileImageApiService,
    private val authApiService: AuthApiService,
) {
    private val logger = KotlinLogging.logger {}

    @PostMapping("/profile/image/advertiser")
    suspend fun uploadAdvertiserProfileImage(
        @RequestHeader("Authorization") authorization: String,
        @RequestPart("meta") meta: UploadAdvertiserProfileImageRequestFromClient,
        @RequestPart("file") file: MultipartFile
    ): ResponseEntity<UploadAdvertiserProfileImageResponseToClient> {
        logger.info { "Validating user authorization for advertiser profile image upload" }
        logger.info { "Received Authorization header: ${authorization.take(50)}..." }

        val extractedUser = authApiService.validateAdvertiser(authorization)
        val userId = extractedUser.userId
        val userType = extractedUser.userType

        if (!userType.name.startsWith("ADVERTISER_")) throw IllegalResourceUsageException(
            message = "uploadAdvertiserProfile request must be called by advertiser user!",
            logics = "AdvertiserProfileImageApiController.uploadAdvertiserProfileImage"
        )

        val result = advertiserProfileImageApiService.uploadProfileImageToImageServer(
            userId,
            userType,
            meta.profileImageType,
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
}
