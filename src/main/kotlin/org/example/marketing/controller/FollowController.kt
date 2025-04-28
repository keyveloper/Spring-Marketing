package org.example.marketing.controller

import jakarta.validation.Valid
import org.example.marketing.domain.user.InfluencerPrincipal
import org.example.marketing.dto.functions.request.FollowRequest
import org.example.marketing.dto.functions.response.FollowResponse
import org.example.marketing.dto.functions.response.GetFollowingAdvertiserInfosResponse
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.service.FollowAdvertiserService
import org.example.marketing.service.FollowService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class FollowController(
    private val followService: FollowService,
    private val followAdvertiserService: FollowAdvertiserService
) {
    @PostMapping("/follow")
    fun follow(
        @Valid @RequestBody followRequest: FollowRequest,
        @AuthenticationPrincipal user: InfluencerPrincipal
    ): ResponseEntity<FollowResponse> {
        return ResponseEntity.ok().body(
            FollowResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                result = followService.switchOrSave(followRequest, user)
            )
        )
    }

    @GetMapping("/follow/following-advertiser-infos")
    fun getAdvertiserInfo(
        @AuthenticationPrincipal user: InfluencerPrincipal
    ): ResponseEntity<GetFollowingAdvertiserInfosResponse> {
        return ResponseEntity.ok().body(
            GetFollowingAdvertiserInfosResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                infos = followAdvertiserService.findAllAdvertiserInfosByInfluencerId(user.userId)
            )
        )
    }
}