package org.example.marketing.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.validation.Valid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asContextElement
import kotlinx.coroutines.withContext
import org.example.marketing.dto.keyword.*
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.service.GoldenKeywordService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class GoldenKeywordController(
    private val goldenKeywordService: GoldenKeywordService,
) {
    private val logger = KotlinLogging.logger {}

    @PostMapping("/test/golden/candidates")
    suspend fun getDugCandidates(
        @Valid @RequestBody request: DigKeywordCandidatesRequest
    ): ResponseEntity<DigKeywordCandidatesResponse> {
        // 1. Capture the current SecurityContext
        val currentContext: SecurityContext = SecurityContextHolder.getContext()

        // 2. Put it into a ThreadLocal
        val threadLocalContext = ThreadLocal<SecurityContext>().apply {
            set(currentContext)
        }

        // 3. Convert that ThreadLocal into a coroutine context element
        val securityElement = threadLocalContext.asContextElement()

        // 4. Run your logic in a coroutine context that includes BOTH the dispatcher and the security element
        return withContext(Dispatchers.Default + securityElement) {
            try {
                val candidates = goldenKeywordService.digCandidates(request)
                ResponseEntity.ok(
                    DigKeywordCandidatesResponse.of(
                        frontErrorCode = FrontErrorCode.OK.code,
                        errorMessage   = FrontErrorCode.OK.message,
                        candidates     = candidates
                    )
                )
            } catch (e: Exception) {
                ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                        DigKeywordCandidatesResponse.of(
                            frontErrorCode = FrontErrorCode.SERVER_CRITICAL.code,
                            errorMessage   = FrontErrorCode.SERVER_CRITICAL.message,
                            candidates     = emptyList()
                        )
                    )
            }
        }
    }

    @PostMapping("/test/golden/top-blogger")
    suspend fun getScrappedTopBlogVisitStat(
        @Valid @RequestBody request: GetScrappedTopBlogVisitStatRequest
    ): ResponseEntity<GetScrappedTopBlogVisitStatResponse> {
        val stat = goldenKeywordService.scrapTopBloggerStat(request)
        return ResponseEntity.ok().body(
            GetScrappedTopBlogVisitStatResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage  = FrontErrorCode.OK.message,
                topBloggerStat = stat
            )
        )
    }
}