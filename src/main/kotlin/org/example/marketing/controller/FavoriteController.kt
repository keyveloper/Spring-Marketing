package org.example.marketing.controller

import org.example.marketing.domain.user.CustomUserPrincipal
import org.example.marketing.domain.user.InfluencerPrincipal
import org.example.marketing.dto.functions.request.FavoriteAdRequest
import org.example.marketing.dto.functions.response.FavoriteAdResponse
import org.example.marketing.dto.functions.response.GetFavoriteAdsResponse
import org.example.marketing.dto.functions.response.GetInfluencerPersonalFavoriteAdsResponse
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.service.AdvertisementFavoriteService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
class FavoriteController(
    private val advertisementFavoriteService: AdvertisementFavoriteService
) {
    @PostMapping("/favorite")
    fun favoriteAd(
        @RequestBody request: FavoriteAdRequest,
        @AuthenticationPrincipal user: UserDetails?
    ): ResponseEntity<FavoriteAdResponse> {
        val userPrincipal = user as CustomUserPrincipal
        return ResponseEntity.ok().body(
            FavoriteAdResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                result = advertisementFavoriteService.switchOrSave(request, userPrincipal)
            )
        )
    }

    @GetMapping("/favorite/ads")
    fun getAllAds(
        @AuthenticationPrincipal user: UserDetails?
    ): ResponseEntity<GetFavoriteAdsResponse> {
        val userPrincipal = user as CustomUserPrincipal
        return ResponseEntity.ok().body(
            GetFavoriteAdsResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                packages = advertisementFavoriteService.findAllAdByInfluencerId(
                    userPrincipal
                )
            )
        )
    }

    @GetMapping("/favorite/ads/influencer-personal")
    fun getAllAdsWithThumbnail(
        @AuthenticationPrincipal influencerPrincipal: InfluencerPrincipal
    ): ResponseEntity<GetInfluencerPersonalFavoriteAdsResponse> {
        return ResponseEntity.ok().body(
            GetInfluencerPersonalFavoriteAdsResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                result  = advertisementFavoriteService.findAllAdWithThumbnailByInfluencerId(
                    influencerPrincipal.userId
                )
            )
        )
    }
}