package org.example.marketing.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.validation.Valid
import org.example.marketing.domain.user.AdvertiserPrincipal
import org.example.marketing.dto.board.request.GetDeliveryAdvertisementsTimelineByCategoryRequest
import org.example.marketing.dto.board.request.MakeNewAdvertisementDeliveryRequest
import org.example.marketing.dto.board.response.GetDeliveryAdvertisementsTimelineByCategoryResponse
import org.example.marketing.dto.board.response.MakeNewAdvertisementDeliveryResponse
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.service.AdvertisementDeliveryService
import org.example.marketing.service.AuthApiService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
private val logger = KotlinLogging.logger {}
@RestController
class AdvertisementDeliveryController(
    private val advertisementDeliveryService: AdvertisementDeliveryService,
    private val authApiService: AuthApiService,
) {
    @PostMapping("/advertisement/delivery")
    suspend fun save(
        @RequestHeader("Authorization") authorization: String,
        @Valid @RequestBody request: MakeNewAdvertisementDeliveryRequest
    ): ResponseEntity<MakeNewAdvertisementDeliveryResponse> {
        logger.info { "Validating advertiser authorization for advertisement creation" }
        val extractedAdvertiser = authApiService.validateAdvertiser(authorization)
        logger.info { "Advertiser validated: userId=${extractedAdvertiser.userId}, email=${extractedAdvertiser.email}" }
        val extractedAdvertiserId = extractedAdvertiser.userId

        return ResponseEntity.ok().body(
            MakeNewAdvertisementDeliveryResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                createdId = advertisementDeliveryService.save(
                    extractedAdvertiserId,
                    request
                )
            )
        )
    }

    @PostMapping("/open/advertisement/deliveries-timeline/by-category")
    fun getAllTimelineByCategory(
       @RequestBody request: GetDeliveryAdvertisementsTimelineByCategoryRequest
    ): ResponseEntity<GetDeliveryAdvertisementsTimelineByCategoryResponse> {
        return ResponseEntity.ok().body(
            GetDeliveryAdvertisementsTimelineByCategoryResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                packages = advertisementDeliveryService.findAllByCategoryAndTimelineDirection(
                    request
                )
            )
        )
    }
}