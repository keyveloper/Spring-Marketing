package org.example.marketing.controller

import org.example.marketing.dto.board.response.AdvertisementInitResult
import org.example.marketing.service.AdvertisementEventService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/init")
class AdvertisementInitController(
    private val advertisementEventService: AdvertisementEventService
) {

    @GetMapping("/advertisement")
    suspend fun getInit(): ResponseEntity<AdvertisementInitResult> {

        return ResponseEntity.ok(initData)
    }
}
