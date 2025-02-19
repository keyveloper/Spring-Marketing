package org.example.marketing.controller

import org.example.marketing.dto.user.request.GetAdvertiserInfoRequest
import org.example.marketing.dto.user.request.MakeNewAdvertiserRequest
import org.example.marketing.dto.user.response.GetAdvertiserInfoResponse
import org.example.marketing.dto.user.response.MakeNewAdvertiserResponse
import org.example.marketing.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AdvertiserController(
    private val userService: UserService
) {
    @PostMapping
    fun makeAdvertiser(
        request: MakeNewAdvertiserRequest
    ): ResponseEntity<MakeNewAdvertiserResponse{

    }

    @GetMapping
    fun getAdvertiserInfo(
        request: GetAdvertiserInfoRequest
    ): ResponseEntity<GetAdvertiserInfoResponse> {

    }
}