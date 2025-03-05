package org.example.marketing.controller

import org.example.marketing.dto.user.request.LoginAdminRequest
import org.example.marketing.dto.user.response.LoginAdminResponse
import org.example.marketing.enum.FrontErrorCode
import org.example.marketing.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    private val authService: AuthService
) {

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
}