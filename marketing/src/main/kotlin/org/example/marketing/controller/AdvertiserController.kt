package org.example.marketing.controller

import org.example.marketing.dto.user.request.GetAdvertiserInfoRequest
import org.example.marketing.dto.user.request.MakeNewAdvertiserRequest
import org.example.marketing.dto.user.response.GetAdvertiserInfoResponse
import org.example.marketing.dto.user.response.MakeNewAdvertiserResponse
import org.example.marketing.entity.user.Advertiser
import org.example.marketing.enum.FrontErrorCode
import org.example.marketing.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class AdvertiserController(
    private val userService: UserService
) {
    @PostMapping("/test/advertiser")
    fun makeAdvertiser(
        @RequestBody request: MakeNewAdvertiserRequest
    ): ResponseEntity<MakeNewAdvertiserResponse> {
        val newId = userService.saveAdvertiser(request)

        return if (newId != null) {
            ResponseEntity.status(HttpStatus.CREATED).body(
                MakeNewAdvertiserResponse.of(
                    FrontErrorCode.OK.code,
                    FrontErrorCode.OK.message,
                    newId
                )
            )
        } else {
            ResponseEntity.status(HttpStatus.CONFLICT).build()
        }
    }


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