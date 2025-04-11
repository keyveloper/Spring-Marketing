package org.example.marketing.controller

import jakarta.validation.Valid
import org.example.marketing.dto.naver.NaverAdApiParameter
import org.example.marketing.dto.naver.NaverOpenApiParameter
import org.example.marketing.service.NaverOpenAPIService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class KeywordController(
    private val naverOpenAPIService: NaverOpenAPIService
) {

    @GetMapping("/test/keywords/blog")
    fun getBlogData(
        @Valid @RequestBody parameter: NaverOpenApiParameter
    ): ResponseEntity<String> {
        return ResponseEntity.ok().body(
            naverOpenAPIService.fetchBlogData(parameter).block()
        )
    }

    @GetMapping("/test/keywords/ad")
    fun getAdData(
        @Valid @RequestBody parameter: NaverAdApiParameter
    ): ResponseEntity<String> {
        return ResponseEntity.ok().body(
            naverOpenAPIService.fetchRelKwdStatData(parameter).block()
        )
    }
}