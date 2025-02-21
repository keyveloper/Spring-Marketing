package org.example.marketing.controller

import org.example.marketing.dto.auth.LogOutRequest
import org.example.marketing.dto.auth.LogOutResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController {

    @PostMapping("/log-out")
    fun logOut(
        @RequestBody request: LogOutRequest
    ): ResponseEntity<LogOutResponse> {

    }
}