package org.example.marketing.controller

import jakarta.validation.Valid
import org.example.marketing.dto.board.request.MakeNewAdvertisementRequest
import org.example.marketing.dto.board.response.MakeNewAdvertisementResponse
import org.example.marketing.service.AdvertisementService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AdvertisementController(
    private val advertisementService: AdvertisementService
) {
    @PostMapping("/test/advertisement")
    fun save(
        @Valid @RequestBody request: MakeNewAdvertisementRequest
    ): ResponseEntity<MakeNewAdvertisementResponse> {

    }
}