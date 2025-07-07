package org.example.marketing.controller

import org.example.marketing.dto.board.response.AdvertisementInitResponse
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.service.AdvertisementInitService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/init")
class AdvertisementInitController(
    private val advertisementInitService: AdvertisementInitService,
) {

    @GetMapping("/advertisement")
    suspend fun getInit(): ResponseEntity<AdvertisementInitResponse> {
        val result = advertisementInitService.findInitAdWithThumbnail()

        return ResponseEntity.ok().body(
            AdvertisementInitResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                result = result
            )
        )
    }
}
