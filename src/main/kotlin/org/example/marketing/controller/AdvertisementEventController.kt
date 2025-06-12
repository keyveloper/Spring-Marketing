package org.example.marketing.controller

import org.example.marketing.dto.board.response.GetAdvertisementDeadlineResponse
import org.example.marketing.dto.board.response.GetAdvertisementFreshResponse
import org.example.marketing.dto.board.response.GetHotAdvertisementResponse
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.service.AdvertisementEventService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AdvertisementEventController(
    private val advertisementEventService: AdvertisementEventService
) {
    @GetMapping("/open/advertisements/fresh")
    fun getFresh(): ResponseEntity<GetAdvertisementFreshResponse> {
        return ResponseEntity.ok().body(
            GetAdvertisementFreshResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                packages = advertisementEventService.findFreshAll()
            )
        )
    }

    @GetMapping("/open/advertisements/deadline")
    fun getDeadline(): ResponseEntity<GetAdvertisementDeadlineResponse> {
        return ResponseEntity.ok().body(
            GetAdvertisementDeadlineResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                packages = advertisementEventService.findDeadlineAll()
            )
        )
    }
}