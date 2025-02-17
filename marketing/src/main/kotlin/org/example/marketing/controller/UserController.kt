package org.example.marketing.controller

import org.example.marketing.dto.user.MakeUserRequest
import org.example.marketing.dto.user.MakeUserResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController {

    @PostMapping
    fun makeNewUser(request: MakeUserRequest): ResponseEntity<MakeUserResponse> {

    }

    @GetMapping
    fun getUserProfile() {

    }
}