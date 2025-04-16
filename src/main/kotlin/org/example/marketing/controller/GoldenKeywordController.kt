package org.example.marketing.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.validation.Valid
import kotlinx.coroutines.runBlocking
import org.example.marketing.dto.keyword.*
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.service.GoldenKeywordService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.async.WebAsyncTask
import java.util.concurrent.Callable

@RestController
class GoldenKeywordController(
    private val goldenKeywordService: GoldenKeywordService
) {
    private val logger = KotlinLogging.logger {}
    @GetMapping("/test/keywords/golden")
    suspend fun getGoldenKeyword(
        @Valid @RequestBody request: GetGoldenKeywordsRequest
    ): WebAsyncTask<ResponseEntity<GetGoldenKeywordsResponse>> {
        val timeoutInMillis = 5 * 60 * 1000L // 5분

        val task = Callable {
            runBlocking {
                try {
                    val result = goldenKeywordService.getGoldenKeywords(request)
                    ResponseEntity.ok(
                        GetGoldenKeywordsResponse.of(
                            frontErrorCode = FrontErrorCode.OK.code,
                            errorMessage = FrontErrorCode.OK.message,
                            goldenKeywords = result
                        )
                    )
                } catch (e: Exception) {
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                        GetGoldenKeywordsResponse.of(
                            frontErrorCode = FrontErrorCode.SERVER_CRITICAL.code,
                            errorMessage = FrontErrorCode.SERVER_CRITICAL.message,
                            goldenKeywords = emptyList()
                        )
                    )
                }
            }
        }

        val asyncTask = WebAsyncTask(timeoutInMillis, task)

        asyncTask.onTimeout {
            ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(
                GetGoldenKeywordsResponse.of(
                    frontErrorCode = FrontErrorCode.SERVER_CRITICAL.code,
                    errorMessage = FrontErrorCode.SERVER_CRITICAL.message,
                    goldenKeywords = emptyList()
                )
            )
        }

        asyncTask.onError {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                GetGoldenKeywordsResponse.of(
                    frontErrorCode = FrontErrorCode.SERVER_CRITICAL.code,
                    errorMessage = FrontErrorCode.SERVER_CRITICAL.message,
                    goldenKeywords = emptyList()
                )
            )
        }

        return asyncTask
    }

    @GetMapping("/test/naver-ad")
    suspend fun adApiTest(
        @RequestBody parameter: NaverAdApiParameter
    ): ResponseEntity<List<RelatedKeywordFromNaverAdServer>> {
        return ResponseEntity.ok().body(
            goldenKeywordService.testNaverAdApi(parameter)
        )
    }
}