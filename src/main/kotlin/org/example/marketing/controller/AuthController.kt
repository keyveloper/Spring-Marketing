package org.example.marketing.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.domain.user.CustomUserPrincipal
import org.example.marketing.dto.user.ValidateTokenResponse
import org.example.marketing.dto.user.ValidateTokenResult
import org.example.marketing.dto.user.request.LoginAdminRequest
import org.example.marketing.dto.user.request.LoginAdvertiserRequest
import org.example.marketing.dto.user.request.LoginInfluencerRequest
import org.example.marketing.dto.user.response.LoginAdminResponse
import org.example.marketing.dto.user.response.LoginAdvertiserResponse
import org.example.marketing.dto.user.response.LoginInfluencerResponse
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    private val authService: AuthService,
) {
    private val logger = KotlinLogging.logger {}
    @PostMapping("/test/login/admin")
    fun loginAdmin(
        @RequestBody request: LoginAdminRequest
    ): ResponseEntity<LoginAdminResponse> {
        return ResponseEntity.ok().body(
            LoginAdminResponse.of(
                FrontErrorCode.OK.code,
                FrontErrorCode.OK.message,
                authService.loginAdmin(request)
            )
        )
    }

    @PostMapping("/entry/login-advertiser")
    fun loginAdvertiser(
        @RequestBody request: LoginAdvertiserRequest
    ): ResponseEntity<LoginAdvertiserResponse> {
        return ResponseEntity.ok().body(
            LoginAdvertiserResponse.of(
                FrontErrorCode.OK.code,
                FrontErrorCode.OK.message,
                authService.loginAdvertiser(request)
            )
        )
    }

    @PostMapping("/entry/login-influencer")
    fun loginInfluencer(
        @RequestBody request: LoginInfluencerRequest
    ): ResponseEntity<LoginInfluencerResponse> {
        return ResponseEntity.ok().body(
            LoginInfluencerResponse.of(
                FrontErrorCode.OK.code,
                FrontErrorCode.OK.message,
                authService.loginInfluencer(request)
            )
        )
    }

    @PostMapping("/validate-token")
    fun validateToken(
        @AuthenticationPrincipal user: UserDetails?
    ): ResponseEntity<ValidateTokenResponse> {
        val principal = user as CustomUserPrincipal
        logger.info { "user: $user" }
        logger.info { "principal: $principal" }

        return ResponseEntity.ok().body(
            ValidateTokenResponse.of(
                validateResult = ValidateTokenResult.of(
                    user.userId ,
                    user.userType,
                    true
                ),
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message
            )
        )
    }
}