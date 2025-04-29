package org.example.marketing.controller

import jakarta.validation.Valid
import org.example.marketing.domain.user.CustomUserPrincipal
import org.example.marketing.dto.functions.request.FavoriteAdRequest
import org.example.marketing.dto.functions.request.GetFavoriteAdsRequest
import org.example.marketing.dto.functions.response.FavoriteAdResponse
import org.example.marketing.dto.functions.response.GetFavoriteAdsResponse
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.service.FavoriteService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
class FavoriteController(
    private val favoriteService: FavoriteService
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
                result = favoriteService.switchOrSave(request, userPrincipal)
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
                packages = favoriteService.findAllAdByInfluencerId(
                    userPrincipal
                )
            )
        )
    }
}