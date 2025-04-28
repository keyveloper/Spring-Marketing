package org.example.marketing.controller

import jakarta.validation.Valid
import org.example.marketing.dto.user.request.GetAdvertiserInfoRequest
import org.example.marketing.dto.user.request.MakeNewAdvertiserRequest
import org.example.marketing.dto.user.response.AdvertiserProfileResponse
import org.example.marketing.dto.user.response.GetAdvertiserInfoResponse
import org.example.marketing.dto.user.response.MakeNewAdvertiserResponse
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.service.AdvertiserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class AdvertiserController(
    private val advertiserService: AdvertiserService
) {
    @PostMapping("/test/advertiser")
    fun save(
        @Valid @RequestBody request: MakeNewAdvertiserRequest
    ): ResponseEntity<MakeNewAdvertiserResponse> {
        return ResponseEntity.ok().body(
            MakeNewAdvertiserResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                advertiserService.save(request)
            )
        )
    }

    @GetMapping("/test/advertiser")
    fun findUserInfo(
        @Valid @RequestBody request: GetAdvertiserInfoRequest
    ): ResponseEntity<GetAdvertiserInfoResponse> {
        return ResponseEntity.ok().body(
            GetAdvertiserInfoResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                advertiser = advertiserService.findUserInfo(request.advertiserId)
            )
        )
    }

    @GetMapping("/test/advertiser/profile")
    fun findUserProfile(
        @RequestParam advertiserId: Long,
    ): ResponseEntity<AdvertiserProfileResponse> {
        return  ResponseEntity.ok().body(
            AdvertiserProfileResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                result = advertiserService.findProfiledById(advertiserId)
            )
        )
    }
}