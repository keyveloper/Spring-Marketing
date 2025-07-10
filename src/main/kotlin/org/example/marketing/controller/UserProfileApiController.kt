package org.example.marketing.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.dto.user.request.UploadProfileImageRequestFromClient
import org.example.marketing.dto.user.response.UploadProfileImageResponseToClient
import org.example.marketing.enums.FrontErrorCode
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

    @PostMapping("/profile/image")
    suspend fun uploadAdvertiserProfileImage(
        @RequestHeader("Authorization") authorization: String,
        @RequestPart("meta") meta: UploadProfileImageRequestFromClient,
        @RequestPart("file") file: MultipartFile
    ): ResponseEntity<UploadProfileImageResponseToClient> {
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


        val result = userProfileApiService.uploadProfileImage()

        return ResponseEntity.ok().body(
            UploadProfileImageResponseToClient.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                result = result
            )
        )
    }
}
