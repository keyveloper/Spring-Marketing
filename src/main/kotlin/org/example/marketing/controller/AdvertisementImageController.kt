package org.example.marketing.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.domain.user.CustomUserPrincipal
import org.example.marketing.dto.board.request.UploadAdvertisementImageRequestFromClient
import org.example.marketing.dto.board.response.FetchAdvertisementImageResponseToClient
import org.example.marketing.dto.board.response.UploadAdvertisementImageResponseToClient
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.service.AdvertisementImageApiService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class AdvertisementImageController(
    private val advertisementImageApiService: AdvertisementImageApiService,
) {
    val logger = KotlinLogging.logger {}

    @PostMapping("/image/advertisement")
    suspend fun uploadImage(
        @AuthenticationPrincipal userPrincipal: CustomUserPrincipal,
        @RequestPart("meta") meta: UploadAdvertisementImageRequestFromClient,
        @RequestPart("file") file: MultipartFile
    ): ResponseEntity<UploadAdvertisementImageResponseToClient> {
        // Capture authentication before async execution
        val userId = userPrincipal.userId
        val draftId = meta.advertisementDraftId
        val isThumbnail = meta.isThumbnail

        val result = advertisementImageApiService.uploadToImageServer(
            userId =userId,
            advertisementDraftId = draftId,
            isThumbnail = isThumbnail,
            file = file
        )

        /***
         TODO: Should Add Exception when userType equals INFLUENCER....! 👉 add validation
        ***/

        return ResponseEntity.ok().body(
            UploadAdvertisementImageResponseToClient(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                result = result
            )
        )
    }

    @GetMapping("/open/image/advertisement/{adId}")
    suspend fun fetchImageByAdId(
        @PathVariable("adId") adId: Long,
    ): ResponseEntity<FetchAdvertisementImageResponseToClient> {
        val result = advertisementImageApiService.fetchImageByAdvertisementId(adId)

        return ResponseEntity.ok().body(
            FetchAdvertisementImageResponseToClient.of(
                result = result,
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
            )
        )
    }
}