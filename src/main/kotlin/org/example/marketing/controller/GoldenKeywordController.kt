package org.example.marketing.controller

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
    @GetMapping("/test/keywords/golden")
    suspend fun getGoldenKeyword(
        @Valid @RequestBody request: GetGoldenKeywordsRequest
    ): ResponseEntity<GetGoldenKeywordsResponse> {
        return ResponseEntity.ok().body(
            GetGoldenKeywordsResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                goldenKeywords = goldenKeywordService.getGoldenKeywords(request)
            )
        )
    }
}