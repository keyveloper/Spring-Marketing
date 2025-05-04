package org.example.marketing.controller

import jakarta.validation.Valid
import org.example.marketing.domain.user.AdvertiserPrincipal
import org.example.marketing.dto.board.request.GetDeliveryAdvertisementsTimelineByCategoryRequest
import org.example.marketing.dto.board.request.MakeNewAdvertisementDeliveryRequest
import org.example.marketing.dto.board.response.GetDeliveryAdvertisementsTimelineByCategoryResponse
import org.example.marketing.dto.board.response.MakeNewAdvertisementDeliveryResponse
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.service.AdvertisementDeliveryService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AdvertisementDeliveryController(
    private val advertisementDeliveryService: AdvertisementDeliveryService,
) {
    @PostMapping("/advertisement/delivery")
    fun save(
        @AuthenticationPrincipal advertiserPrincipal: AdvertiserPrincipal,
        @Valid @RequestBody request: MakeNewAdvertisementDeliveryRequest
    ): ResponseEntity<MakeNewAdvertisementDeliveryResponse> {
        return ResponseEntity.ok().body(
            MakeNewAdvertisementDeliveryResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                createdId = advertisementDeliveryService.save(
                    advertiserPrincipal.userId,
                    request
                )
            )
        )
    }

    @PostMapping("/test/advertisement/deliveries-timeline/by-category")
    fun getAllTimelineByCategory(
       @RequestBody request: GetDeliveryAdvertisementsTimelineByCategoryRequest
    ): ResponseEntity<GetDeliveryAdvertisementsTimelineByCategoryResponse> {
        return ResponseEntity.ok().body(
            GetDeliveryAdvertisementsTimelineByCategoryResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                advertisements = advertisementDeliveryService.findAllByCategoryAndTimelineDirection(
                    request
                )
            )
        )
    }
}