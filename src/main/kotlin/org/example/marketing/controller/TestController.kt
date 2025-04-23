package org.example.marketing.controller

import org.example.marketing.dto.keyword.GetKeywordCombinationRequest
import org.example.marketing.service.OpenAIApiService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(
    private val openAIApiService: OpenAIApiService
) {
    @GetMapping("/test/gpt")
    suspend fun test(
        @RequestBody request: GetKeywordCombinationRequest
    ): ResponseEntity<String?> {
        return ResponseEntity.ok().body(
            openAIApiService.fetchKeywordCombinations(
                request.keyword,
                request.context
            )
        )
    }
}