package org.example.marketing.controller

import org.apache.tika.metadata.HttpHeaders
import org.example.marketing.domain.user.AdvertiserPrincipal
import org.example.marketing.dto.functions.response.GetOwnedExpiredAdvertisementsResponse
import org.example.marketing.dto.functions.response.GetOwnedLiveAdvertisementsResponse
import org.example.marketing.dto.user.request.UploadUserProfileImageRequestFromClient
import org.example.marketing.dto.user.request.MakeNewAdvertiserProfileInfoRequest
import org.example.marketing.dto.user.response.*
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.service.AdvertiserProfileInfoService
import org.example.marketing.service.UserProfileImageApiService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
class AdvertiserProfileController(
    private val userProfileImageApiService: UserProfileImageApiService,
    private val advertiserProfileInfoService: AdvertiserProfileInfoService
) {

//    @PostMapping(
//        "/advertiser/image/profile",
//        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
//    )
//    fun saveProfileImage(
//        @AuthenticationPrincipal advertiserPrincipal: AdvertiserPrincipal,
//        @RequestPart("meta") meta: UploadUserProfileImageRequestFromClient,
//        @RequestPart("file") file: MultipartFile
//    ): ResponseEntity<MakeNewAdvertiserProfileImageResponse> {
//        return ResponseEntity.ok().body(
//            MakeNewAdvertiserProfileImageResponse.of(
//                FrontErrorCode.OK.code,
//                FrontErrorCode.OK.message,
//                userProfileImageApiService.save(
//                    advertiserId = advertiserPrincipal.userId,
//                    meta = meta,
//                    file = file
//                )
//            )
//        )
//    }
//
//    @PostMapping("/advertiser/image/commit/{entityId}")
//    fun commitImageById(
//        @AuthenticationPrincipal advertiserPrincipal: AdvertiserPrincipal,
//        @PathVariable("entityId") entityId: Long,
//    ): ResponseEntity<CommitAdvertiserProfileImageResponse> {
//        return ResponseEntity.ok().body(
//            CommitAdvertiserProfileImageResponse.of(
//                FrontErrorCode.OK.code,
//                FrontErrorCode.OK.message,
//                userProfileImageApiService.commit(
//                    advertiserPrincipal.userId,
//                    entityId
//                )
//            )
//        )
//    }
//
//    // 📌 do not use !
//    @GetMapping("/open/advertiser/image/profile/{unifiedCode}")
//    fun getProfileImage(
//        @PathVariable("unifiedCode") unifiedCode: String,
//    ): ResponseEntity<ByteArray> {
//        val result = userProfileImageApiService.findByUnifiedCode(unifiedCode)
//        return ResponseEntity.ok()
//            .header(
//                HttpHeaders.CONTENT_DISPOSITION,
//                "inline; filename=\"test.${result.imageType}\""
//            )
//            .contentType(MediaType.parseMediaType(result.imageType))
//            .body(result.imageBytes)
//    }

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

    @GetMapping("/advertiser/profile/owned")
    fun getOwnedProfileInfo(
        @AuthenticationPrincipal advertiserPrincipal: AdvertiserPrincipal
    ): ResponseEntity<GetAdvertiserOwnedProfileResponse> {
        return ResponseEntity.ok().body(
            GetAdvertiserOwnedProfileResponse.of(
                FrontErrorCode.OK.code,
                FrontErrorCode.OK.message,
                advertiserProfileInfoService.findProfileInfoByAdvertiserId(advertiserPrincipal.userId)
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

    @GetMapping("/open/advertiser/profile/live-ads/{advertiserId}")
    fun getAllLiveAdvertisements(
        @PathVariable("advertiserId") advertiserId: Long,
    ): ResponseEntity<GetOwnedLiveAdvertisementsResponse> {
        return ResponseEntity.ok().body(
            GetOwnedLiveAdvertisementsResponse.of(
                FrontErrorCode.OK.code,
                FrontErrorCode.OK.message,
                advertiserProfileInfoService.findLiveAllAdsByAdvertisements(advertiserId)
            )
        )
    }

    @GetMapping("/open/advertiser/profile/expired-ads/{advertiserId}")
    fun getAllExpiredAdvertisements(
        @PathVariable("advertiserId") advertiserId: Long,
    ): ResponseEntity<GetOwnedExpiredAdvertisementsResponse> {
        return ResponseEntity.ok().body(
            GetOwnedExpiredAdvertisementsResponse.of(
                FrontErrorCode.OK.code,
                FrontErrorCode.OK.message,
                advertiserProfileInfoService.findExpiredAllAdsByAdvertisements(advertiserId)
            )
        )
    }

}