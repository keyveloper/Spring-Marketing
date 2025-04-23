@file:Suppress("UNREACHABLE_CODE")

package org.example.marketing.service

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import io.github.resilience4j.kotlin.circuitbreaker.executeSuspendFunction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.example.marketing.dto.keyword.Prompts.BASE_PROMPT
import org.springframework.ai.chat.client.ChatClient
import org.springframework.stereotype.Service

@Service
class OpenAIApiService(
    private val chatClient: ChatClient,
    private val circuitBreakerRegistry: CircuitBreakerRegistry
) {
    private val logger = KotlinLogging.logger {}
    private val circuitBreaker = circuitBreakerRegistry.circuitBreaker("openAiApiCircuitBreaker")
    @CircuitBreaker(
        name = "openAiApiCircuitBreaker",
    )
    suspend fun fetchKeywordCombinations(
        keyword: String,
        context: String
    ): String? {
        val rule = BASE_PROMPT
        return try {
            circuitBreaker.executeSuspendFunction {
                withContext(Dispatchers.IO) {          // ★ blocking → IO thread
                    chatClient.prompt()
                        .system(rule)
                        .user("keyword: $keyword + context: $context")
                        .call()                        // 동기 호출
                        .content()                    // String 응답
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
    ): String? {
        logger.error { "openAiApiCircuitBreaker error: ${throwable.message}" }
        return null
    }
}