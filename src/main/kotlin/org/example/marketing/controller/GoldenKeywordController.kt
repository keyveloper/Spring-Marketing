package org.example.marketing.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.validation.Valid
import org.example.marketing.dto.keyword.*
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.service.GoldenKeywordService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class GoldenKeywordController(
    private val goldenKeywordService: GoldenKeywordService
) {
    private val logger = KotlinLogging.logger {}
    @GetMapping("/test/keywords/golden")
    suspend fun getGoldenKeyword(
        @Valid @RequestBody request: GetGoldenKeywordsRequest
    ): ResponseEntity<GetGoldenKeywordsResponse> {
        val goldenKeywords = goldenKeywordService.getGoldenKeywords(request)
        logger.info { "response body: $goldenKeywords" }
        return ResponseEntity.ok().body(
            GetGoldenKeywordsResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                goldenKeywords = goldenKeywords
            )
        )
    }
}