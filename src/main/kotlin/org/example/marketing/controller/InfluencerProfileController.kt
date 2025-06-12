package org.example.marketing.controller

import org.apache.tika.metadata.HttpHeaders
import org.example.marketing.domain.user.InfluencerPrincipal
import org.example.marketing.dto.user.request.MakeNewInfluencerProfileImageRequest
import org.example.marketing.dto.user.request.MakeNewInfluencerProfileInfoRequest
import org.example.marketing.dto.user.response.*
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.service.InfluencerProfileImageService
import org.example.marketing.service.InfluencerProfileService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
class InfluencerProfileController(
    private val influencerProfileImageService: InfluencerProfileImageService,
    private val influencerProfileService: InfluencerProfileService
) {

    @PostMapping(
        "/influencer/image/profile",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE]
    )
    fun saveProfileImage(
        @AuthenticationPrincipal influencerPrincipal: InfluencerPrincipal,
        @RequestPart("meta") meta: MakeNewInfluencerProfileImageRequest,
        @RequestPart("file") file: MultipartFile
    ): ResponseEntity<MakeNewInfluencerProfileImageResponse> {
        return ResponseEntity.ok().body(
            MakeNewInfluencerProfileImageResponse.of(
                FrontErrorCode.OK.code,
                FrontErrorCode.OK.message,
                influencerProfileImageService.save(influencerPrincipal.userId, meta, file)
            )
        )
    }

    @PostMapping("/influencer/image/profile/commit/{entityId}")
    fun commitProfileImageById(
        @AuthenticationPrincipal influencerPrincipal: InfluencerPrincipal,
        @PathVariable("entityId") entityId: Long,
    ): ResponseEntity<CommitInfluencerProfileImageResponse> {
        return ResponseEntity.ok().body(
            CommitInfluencerProfileImageResponse.of(
                FrontErrorCode.OK.code,
                FrontErrorCode.OK.message,
                influencerProfileImageService.commit(influencerPrincipal.userId, entityId)
            )
        )
    }

    @GetMapping("/open/influencer/image/profile/{unifiedCode}")
    fun getProfileImage(
        @PathVariable("unifiedCode") unifiedCode: String,
    ): ResponseEntity<ByteArray> {
        val result = influencerProfileImageService.findByUnifiedCode(unifiedCode)
        return ResponseEntity.ok()
            .header(
                HttpHeaders.CONTENT_DISPOSITION,
                "inline; filename=\"test.${result.imageType}\"")
            .contentType(MediaType.parseMediaType(result.imageType))
            .body(result.imageBytes)
    }

    @PostMapping("/influencer/profile/info")
    fun saveProfileInfo(
        @AuthenticationPrincipal influencerPrincipal: InfluencerPrincipal,
        @RequestBody request: MakeNewInfluencerProfileInfoRequest
    ): ResponseEntity<MakeNewInfluencerProfileInfoResponse> {
        return ResponseEntity.ok().body(
            MakeNewInfluencerProfileInfoResponse.of(
                FrontErrorCode.OK.code,
                FrontErrorCode.OK.message,
                influencerProfileService.saveProfileInfo(influencerPrincipal.userId, request)
            )
        )
    }

    @GetMapping("/open/influencer/profile/info/{influencerId}")
    fun getProfileInfo(
        @PathVariable("influencerId") influencerId: Long,
    ): ResponseEntity<GetInfluencerProfileResponse> {
        return ResponseEntity.ok().body(
            GetInfluencerProfileResponse.of(
                FrontErrorCode.OK.code,
                FrontErrorCode.OK.message,
                influencerProfileService.findProfileInfoByInfluencerId(influencerId)
            )
        )
    }

    @GetMapping("/influencer/profile/owned")
    fun getOwnedProfileInfo(
        @AuthenticationPrincipal influencerPrincipal: InfluencerPrincipal
    ): ResponseEntity<GetInfluencerOwnedProfileResponse> {
        return ResponseEntity.ok().body(
            GetInfluencerOwnedProfileResponse.of(
                FrontErrorCode.OK.code,
                FrontErrorCode.OK.message,
                influencerProfileService.findProfileInfoByInfluencerId(influencerPrincipal.userId)
            )
        )
    }
}
