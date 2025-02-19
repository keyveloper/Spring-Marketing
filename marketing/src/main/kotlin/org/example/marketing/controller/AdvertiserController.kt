package org.example.marketing.controller

import org.example.marketing.dto.user.request.GetAdvertiserInfoRequest
import org.example.marketing.dto.user.response.GetAdvertiserInfoResponse
import org.example.marketing.entity.user.Advertiser
import org.example.marketing.enum.FrontErrorCode
import org.example.marketing.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class AdvertiserController(
    private val userService: UserService
) {
//    @PostMapping
////    fun makeAdvertiser(
////        request: MakeNewAdvertiserRequest
////    ): ResponseEntity<MakeNewAdvertiserResponse{
////
////    }


    @GetMapping("/test/advertiser-all")
    fun findAll(): ResponseEntity<List<GetAdvertiserInfoResponse>> {
        return  ResponseEntity.ok().body(
            userService.findAllAdvertisers().map {
                GetAdvertiserInfoResponse.of(
                    frontErrorCode = FrontErrorCode.OK.code,
                    errorMessage = FrontErrorCode.OK.message,
                    it,
                )
            }
        )
    }

    @GetMapping("/test/advertiser")
    fun findById(
        @RequestParam request: GetAdvertiserInfoRequest
    ): ResponseEntity<GetAdvertiserInfoResponse> {
        val advertiser: Advertiser? = userService.findAdvertiserById(request.advertiserId)
        return if (advertiser != null) {
            ResponseEntity.ok().body(
                GetAdvertiserInfoResponse.of(
                    frontErrorCode = FrontErrorCode.OK.code,
                    errorMessage = FrontErrorCode.OK.message,
                    advertiser
                )
            )
        } else {
            ResponseEntity.notFound().build()
        }
    }
}