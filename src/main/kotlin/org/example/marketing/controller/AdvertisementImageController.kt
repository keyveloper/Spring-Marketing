package org.example.marketing.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.validation.Valid
import org.apache.tika.metadata.HttpHeaders
import org.example.marketing.domain.user.AdvertiserPrincipal
import org.example.marketing.domain.user.CustomUserPrincipal
import org.example.marketing.dto.board.request.MakeNewAdvertisementImageRequest
import org.example.marketing.dto.board.request.SetAdvertisementThumbnailRequest
import org.example.marketing.dto.board.request.UploadAdvertisementImageRequestFromClient
import org.example.marketing.dto.board.response.*
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.service.AdvertisementImageApiService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("image/advertisement")
class AdvertisementImageController(
    private val advertisementImageApiService: AdvertisementImageApiService,
) {
    val logger = KotlinLogging.logger {}

    @PostMapping
    suspend fun uploadImage(
        @AuthenticationPrincipal userPrincipal: CustomUserPrincipal,
        @RequestPart("meta") meta: UploadAdvertisementImageRequestFromClient,
        @RequestPart("file") file: MultipartFile
    ): ResponseEntity<UploadAdvertisementImageResponseToClient> {
        // Capture authentication before async execution
        val userId = userPrincipal.userId
        val isThumbnail = meta.isThumbnail

        val result = advertisementImageApiService.uploadToImageServer(
            userId,
            isThumbnail,
            file
        )

        /***
         TODO: Should Add Exception when userType equals INFLUENCER....!
        ***/

        return ResponseEntity.ok().body(
            UploadAdvertisementImageResponseToClient(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                result = result
            )
        )
    }
}