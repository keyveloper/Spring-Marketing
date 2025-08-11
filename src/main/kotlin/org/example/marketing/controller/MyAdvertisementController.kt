package org.example.marketing.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.dto.board.response.GetMyAdvertisementsResponse
import org.example.marketing.dto.board.response.GetOfferedApplicationsResponse
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.service.AuthApiService
import org.example.marketing.service.MyAdvertisementService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/my-advertisement")
class MyAdvertisementController(
    private val myAdvertisementService: MyAdvertisementService,
    private val authApiService: AuthApiService
) {
    private val logger = KotlinLogging.logger {}

    @GetMapping
    suspend fun getMyAdvertisements(
        @RequestHeader("Authorization") authorization: String
    ): ResponseEntity<GetMyAdvertisementsResponse> {
        logger.info { "GET /my-advertisement - authorization=${authorization.take(20)}..." }

        val extractedUser = authApiService.validateAdvertiser(authorization)
        val advertiserId = extractedUser.userId
        logger.info { "Advertiser validated: advertiserId=$advertiserId" }

        val result = myAdvertisementService.getAdvertisementsByAdvertiserId(advertiserId)

        return ResponseEntity.ok().body(
            GetMyAdvertisementsResponse.of(
                result = result,
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message
            )
        )
    }

    @GetMapping("/offered-applications")
    suspend fun getOfferedApplications(
        @RequestHeader("Authorization") authorization: String
    ): ResponseEntity<GetOfferedApplicationsResponse> {
        logger.info { "GET /my-advertisement/offered-applications - authorization=${authorization.take(20)}..." }

        val extractedUser = authApiService.validateAdvertiser(authorization)
        val advertiserId = extractedUser.userId
        logger.info { "Advertiser validated: advertiserId=$advertiserId" }

        val result = myAdvertisementService.getOfferedApplicationsByAdvertiserId(advertiserId)

        return ResponseEntity.ok().body(
            GetOfferedApplicationsResponse.of(
                result = result,
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message
            )
        )
    }
}
