package org.example.marketing.controller

import jakarta.validation.Valid
import org.example.marketing.domain.user.InfluencerPrincipal
import org.example.marketing.dto.functions.request.FollowAdvertiserRequest
import org.example.marketing.dto.functions.response.FollowAdvertiserResponse
import org.example.marketing.dto.functions.response.GetAdvertiserFollowerInfoResponse
import org.example.marketing.dto.functions.response.GetFollowingAdsWithAdvertiserInfoResponse
import org.example.marketing.dto.functions.response.GetFollowingAdvertiserInfosResponse
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.service.FollowAdvertiserService
import org.example.marketing.service.FollowDslService
import org.example.marketing.service.FollowService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class FollowController(
    private val followService: FollowService,
    private val followDslService: FollowDslService,
    private val followAdvertiserService: FollowAdvertiserService
) {
    @PostMapping("/follow")
    fun follow(
        @Valid @RequestBody followAdvertiserRequest: FollowAdvertiserRequest,
        @AuthenticationPrincipal user: InfluencerPrincipal
    ): ResponseEntity<FollowAdvertiserResponse> {
        return ResponseEntity.ok().body(
            FollowAdvertiserResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                result = followService.switchOrSave(followAdvertiserRequest, user)
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

    @GetMapping("/follow/advertisements/advertisers")
    fun getFollowingAdAndAdvertiserInfo(
        @AuthenticationPrincipal user: InfluencerPrincipal,
    ): ResponseEntity<GetFollowingAdsWithAdvertiserInfoResponse> {
        return ResponseEntity.ok().body(
            GetFollowingAdsWithAdvertiserInfoResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                packages = followDslService.findAdsByInfluencerId(user.userId)
            )
        )
    }

    @GetMapping("/follow/advertiser-follower-info/{advertiserId}")
    fun getAdvertiserFollowerInfo(
        @AuthenticationPrincipal influencerPrincipal: InfluencerPrincipal,
        @PathVariable advertiserId: Long
    ): ResponseEntity<GetAdvertiserFollowerInfoResponse> {
        return ResponseEntity.ok().body(
            GetAdvertiserFollowerInfoResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                info = followAdvertiserService.findAllFollowerInfoByInfluencerId(
                    advertiserId, influencerPrincipal.userId)
            )
        )
    }
}