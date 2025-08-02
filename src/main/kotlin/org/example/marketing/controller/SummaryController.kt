package org.example.marketing.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.dto.user.response.GetAdvertiserProfileSummarizedResponseToClient
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.service.AdvertiserProfileSummaryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/summary")
class SummaryController(
    private val advertiserProfileSummaryService: AdvertiserProfileSummaryService
) {
    private val logger = KotlinLogging.logger {}

    @GetMapping("/advertisement-owner/{advertisementId}")
    suspend fun getAdvertiserProfileByAdvertisementId(
        @PathVariable advertisementId: Long
    ): ResponseEntity<GetAdvertiserProfileSummarizedResponseToClient> {
        logger.info { "Getting advertiser profile summary for advertisementId: $advertisementId" }

        val result = advertiserProfileSummaryService.getAdvertiserProfileByAdvertisementId(advertisementId)

        return ResponseEntity.ok().body(
            GetAdvertiserProfileSummarizedResponseToClient.of(
                result = result,
                frontErrorCode = FrontErrorCode.OK
            )
        )
    }
}
