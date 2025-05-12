package org.example.marketing.controller

import jakarta.validation.Valid
import org.example.marketing.dto.keyword.GenerateSeoOptimizedTitlesRequest
import org.example.marketing.dto.keyword.GenerateSeoOptimizedTitlesResponse
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.service.OpenAIApiService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class OpenAiController(
    private val openAIApiService: OpenAIApiService
) {

    @PostMapping("/test/golden/titles")
    suspend fun generateSeoOptimizedTitles(
        @Valid @RequestBody request: GenerateSeoOptimizedTitlesRequest
    ): ResponseEntity<GenerateSeoOptimizedTitlesResponse> {
        val titles = openAIApiService.fetchGeneratedOptimizedTitles(request)


        return ResponseEntity.ok().body(
            GenerateSeoOptimizedTitlesResponse(
                FrontErrorCode.OK.code,
                FrontErrorCode.OK.message,
                titles ?: listOf()
            )
        )
    }
}