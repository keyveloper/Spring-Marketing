package org.example.marketing.controller

import org.apache.tika.metadata.HttpHeaders
import org.example.marketing.domain.user.AdvertiserPrincipal
import org.example.marketing.dto.user.request.MakeNewAdvertiserProfileImageRequest
import org.example.marketing.dto.user.request.MakeNewAdvertiserProfileInfoRequest
import org.example.marketing.dto.user.response.CommitAdvertiserProfileImageResponse
import org.example.marketing.dto.user.response.GetAdvertiserProfileResponse
import org.example.marketing.dto.user.response.MakeNewAdvertiserProfileImageResponse
import org.example.marketing.dto.user.response.MakeNewAdvertiserProfileInfoResponse
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.service.AdvertiseProfileInfoService
import org.example.marketing.service.AdvertiserProfileImageService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
class AdvertiserProfileController(
    private val advertiserProfileImageService: AdvertiserProfileImageService,
    private val advertiserProfileInfoService: AdvertiseProfileInfoService
) {

    @PostMapping(
        "/advertiser/image/profile",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
    )
    fun saveProfileImage(
        @AuthenticationPrincipal advertiserPrincipal: AdvertiserPrincipal,
        @RequestPart("meta") meta: MakeNewAdvertiserProfileImageRequest,
        @RequestPart("file") file: MultipartFile
    ): ResponseEntity<MakeNewAdvertiserProfileImageResponse> {
        return ResponseEntity.ok().body(
            MakeNewAdvertiserProfileImageResponse.of(
                FrontErrorCode.OK.code,
                FrontErrorCode.OK.message,
                advertiserProfileImageService.save(
                    advertiserId = advertiserPrincipal.userId,
                    meta = meta,
                    file = file
                )
            )
        )
    }

    @PostMapping("/advertiser/image/commit/{entityId}")
    fun commitImageById(
        @AuthenticationPrincipal advertiserPrincipal: AdvertiserPrincipal,
        @PathVariable("entityId") entityId: Long,
    ): ResponseEntity<CommitAdvertiserProfileImageResponse> {
        return ResponseEntity.ok().body(
            CommitAdvertiserProfileImageResponse.of(
                FrontErrorCode.OK.code,
                FrontErrorCode.OK.message,
                advertiserProfileImageService.commit(
                    advertiserPrincipal.userId,
                    entityId
                )
            )
        )
    }

    @GetMapping("/open/advertiser/image/profile/{unifiedCode}")
    fun getProfileImage(
        @PathVariable("unifiedCode") unifiedCode: String,
    ): ResponseEntity<ByteArray> {
        val result = advertiserProfileImageService.findByUnifiedCode(unifiedCode)
        return ResponseEntity.ok()
            .header(
                HttpHeaders.CONTENT_DISPOSITION,
                "inline; filename=\"test.${result.imageType}\"")
            .contentType(MediaType.parseMediaType(result.imageType))
            .body(result.imageBytes)
    }

    @PostMapping("/advertiser/profile/info")
    fun saveProfileInfo(
        @AuthenticationPrincipal advertiserPrincipal: AdvertiserPrincipal,
        @RequestBody request: MakeNewAdvertiserProfileInfoRequest
    ): ResponseEntity<MakeNewAdvertiserProfileInfoResponse> {
        return ResponseEntity.ok().body(
            MakeNewAdvertiserProfileInfoResponse.of(
                FrontErrorCode.OK.code,
                FrontErrorCode.OK.message,
                advertiserProfileInfoService.saveAdditionalInfo(advertiserPrincipal.userId, request)
            )
        )
    }

    @GetMapping("/open/advertiser/profile/info/{advertiserId}")
    fun getProfileInfo(
        @PathVariable("advertiserId") advertiserId: Long,
    ): ResponseEntity<GetAdvertiserProfileResponse> {
        return ResponseEntity.ok().body(
            GetAdvertiserProfileResponse.of(
                FrontErrorCode.OK.code,
                FrontErrorCode.OK.message,
                advertiserProfileInfoService.findProfileInfoByAdvertiserId(advertiserId)
            )
        )
    }
}