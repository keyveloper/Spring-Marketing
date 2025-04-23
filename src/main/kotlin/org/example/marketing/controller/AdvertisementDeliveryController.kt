package org.example.marketing.controller

import jakarta.validation.Valid
import org.example.marketing.dto.board.request.GetDeliveryAdvertisementsTimelineByCategoryRequest
import org.example.marketing.dto.board.request.MakeNewAdvertisementDeliveryRequest
import org.example.marketing.dto.board.response.GetAdvertisementDeliveryResponse
import org.example.marketing.dto.board.response.GetDeliveryAdvertisementsTimelineByCategoryResponse
import org.example.marketing.dto.board.response.MakeNewAdvertisementDeliveryResponse
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.service.AdvertisementDeliveryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AdvertisementDeliveryController(
    private val advertisementDeliveryService: AdvertisementDeliveryService
) {

    @PostMapping("/test/advertisement/delivery")
    fun save(
        @Valid @RequestBody request: MakeNewAdvertisementDeliveryRequest
    ): ResponseEntity<MakeNewAdvertisementDeliveryResponse> {
        return ResponseEntity.ok().body(
            MakeNewAdvertisementDeliveryResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                createdId = advertisementDeliveryService.save(request)
            )
        )
    }

    @GetMapping("/test/advertisement/delivery/{targeId}")
    fun getById(
        @PathVariable targeId: Long
    ): ResponseEntity<GetAdvertisementDeliveryResponse> {
        return ResponseEntity.ok().body(
            GetAdvertisementDeliveryResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                advertisement = advertisementDeliveryService.findById(targeId)
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