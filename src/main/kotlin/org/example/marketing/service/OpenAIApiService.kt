package org.example.marketing.service

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import io.github.resilience4j.kotlin.circuitbreaker.executeSuspendFunction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.example.marketing.dto.keyword.GenerateSeoOptimizedTitlesRequest
import org.example.marketing.dto.keyword.Prompts.GENERATE_TITLE_PROMPT
import org.example.marketing.dto.keyword.Prompts.KEYWORD_COMBINATION_PROMPT
import org.springframework.ai.chat.client.ChatClient
import org.springframework.stereotype.Service

@Service
class OpenAIApiService(
    private val chatClient: ChatClient,
    private val circuitBreakerRegistry: CircuitBreakerRegistry,
    private val keywordFilterService: KeywordFilterService
) {
    private val rule = KEYWORD_COMBINATION_PROMPT
    private val logger = KotlinLogging.logger {}
    private val circuitBreaker = circuitBreakerRegistry.circuitBreaker("openAiApiCircuitBreaker")
    @CircuitBreaker(
        name = "openAiApiCircuitBreaker",
    )
    suspend fun fetchKeywordCombinations(
        keyword: String,
        context: String
    ): List<String>? {
        return try {
            circuitBreaker.executeSuspendFunction {
                withContext(Dispatchers.IO) {          // ★ blocking → IO thread
                    val rawString = chatClient.prompt()
                        .system(rule)
                        .user("keyword: $keyword + context: $context")
                        .call()                        // 동기 호출
                        .content()                    // String 응답\

                    rawString?.let {
                        val result: List<String> = Json.decodeFromString(
                            rawString
                                .removePrefix("```json")
                                .removePrefix("```")
                                .removeSuffix("```")
                        )
                        result
                    }
                }
            }
        } catch (ex: Throwable) {
            logger.error { "FetchKeywordCombination error: ${ex.message}" }
            null
        }
    }


    @CircuitBreaker(
        name = "openAiApiCircuitBreaker",
    )
    suspend fun fetchGeneratedOptimizedTitles(
        request: GenerateSeoOptimizedTitlesRequest
    ): List<String>? {
        val rule = GENERATE_TITLE_PROMPT
        return try {
            circuitBreaker.executeSuspendFunction {
                withContext(Dispatchers.IO) {          // ★ blocking → IO thread
                    val rawString = chatClient.prompt()
                        .system(rule)
                        .user("keyword: ${request.keyword}, description: ${request.description}")
                        .call()                        // 동기 호출
                        .content()                    // String 응답\
                    rawString?.let {
                        val result: List<String> = Json.decodeFromString(
                            rawString
                                .removePrefix("```json")
                                .removePrefix("```")
                                .removeSuffix("```")
                        )
                        result
                    }
                }
            }
        } catch (ex: Throwable) {
            logger.error { "FetchKeywordCombination error: ${ex.message}" }
            null
        }

    }

    fun fetchKeywordCombinationFallback(
        keyword: String,
        context: String,
        throwable: Throwable
    ): List<String>? {
        logger.error { "openAiApiCircuitBreaker error: ${throwable.message}" }
        return null
    }

    fun fetchGeneratedOptimizedTitles(
        request: GenerateSeoOptimizedTitlesRequest,
        throwable: Throwable
    ): List<String>? {
        logger.error { "openAiApiCircuitBreaker error: ${throwable.message}" }
        return null
    }
}