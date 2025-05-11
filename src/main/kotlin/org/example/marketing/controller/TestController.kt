package org.example.marketing.controller

import org.example.marketing.dto.keyword.GetKeywordCombinationRequest
import org.example.marketing.dto.keyword.NaverAdApiParameter
import org.example.marketing.dto.keyword.RelatedKeywordStat
import org.example.marketing.service.OpenAIApiService
import org.example.marketing.service.TestService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(
    private val openAIApiService: OpenAIApiService,
    private val testService: TestService
) {
    @GetMapping("/test/gpt")
    suspend fun test(
        @RequestBody request: GetKeywordCombinationRequest
    ): ResponseEntity<List<String>?> {
        return ResponseEntity.ok().body(
            openAIApiService.fetchKeywordCombinations(
                request.keyword,
                request.context
            )
        )
    }

    @PostMapping("/test/ad")
    suspend fun testAD(
        @RequestBody request: NaverAdApiParameter
    ): ResponseEntity<List<RelatedKeywordStat>> {
        return ResponseEntity.ok().body(
            testService.testNaverAdApi(request)
        )
    }
}