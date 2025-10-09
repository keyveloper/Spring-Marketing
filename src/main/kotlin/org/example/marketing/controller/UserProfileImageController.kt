package org.example.marketing.controller

import org.example.marketing.domain.user.CustomUserPrincipal
import org.example.marketing.dto.user.request.UploadUserProfileImageRequestFromClient
import org.example.marketing.dto.user.response.UploadUserProfileImageResponseToClient
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.service.UserProfileImageApiService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/image/user")
class UserProfileImageController(
    private val userProfileImageApiService: UserProfileImageApiService
) {

    @PostMapping
    suspend fun uploadImage(
        @AuthenticationPrincipal userPrincipal: CustomUserPrincipal,
        @RequestPart("meta") meta: UploadUserProfileImageRequestFromClient,
        @RequestPart("file") file: MultipartFile
    ): ResponseEntity<UploadUserProfileImageResponseToClient> {
        // Capture authentication before async execution
        val userId = userPrincipal.userId
        val userType = userPrincipal.userType
        val profileImageType = meta.profileImageType

        val result = userProfileImageApiService.uploadToImageServer(
            userId,
            userType,
            profileImageType,
            file
        )

        return ResponseEntity.ok().body(
            UploadUserProfileImageResponseToClient(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                result = result
            )
        )
    }
}