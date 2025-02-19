package org.example.marketing.controller

import org.example.marketing.service.UserService
import org.springframework.web.bind.annotation.RestController

@RestController
class AdvertiserController(
    private val userService: UserService
) {
//    @PostMapping
//    fun makeAdvertiser(
//        request: MakeNewAdvertiserRequest
//    ): ResponseEntity<MakeNewAdvertiserResponse{
//
//    }
//
//    @GetMapping
//    fun getAdvertiserInfo(
//        request: GetAdvertiserInfoRequest
//    ): ResponseEntity<GetAdvertiserInfoResponse> {
//
//    }
}