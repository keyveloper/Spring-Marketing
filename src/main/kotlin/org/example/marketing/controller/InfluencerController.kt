package org.example.marketing.controller

import jakarta.validation.Valid
import org.example.marketing.dto.user.request.MakeNewInfluencerRequest
import org.example.marketing.dto.user.response.MakeNewInfluencerResponse
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.service.InfluencerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class InfluencerController(
    private val influencerService: InfluencerService
) {

    @PostMapping("/entry/new-influencer")
    fun save(
        @Valid @RequestBody reqeust: MakeNewInfluencerRequest,
    ): ResponseEntity<MakeNewInfluencerResponse> {
        return ResponseEntity.ok().body(
            MakeNewInfluencerResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                influencerService.save(reqeust)
            )
        )
    }
}