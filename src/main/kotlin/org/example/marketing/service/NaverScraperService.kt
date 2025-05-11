package org.example.marketing.service

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import io.github.resilience4j.kotlin.circuitbreaker.executeSuspendFunction
import org.example.marketing.dto.keyword.GetScrappedStataRequestToScrapperServer
import org.example.marketing.dto.keyword.ScrappedTopBLoggerStatFromScrapperServer
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.util.UriBuilder

@Service
class NaverScraperService(
    @Qualifier("naverScraperClient") private val scraperClient: WebClient,
    private val circuitBreakerRegistry: CircuitBreakerRegistry
) {
    private val logger = KotlinLogging.logger {}
    private val circuitBreaker = circuitBreakerRegistry.circuitBreaker("naverScrapperCircuitBreaker")

    @CircuitBreaker(
        name = "naverScrapperCircuitBreaker",
    )
    suspend fun fetchScrappedTop10BloggerStat(keyword: String): ScrappedTopBLoggerStatFromScrapperServer? {
        return circuitBreaker.executeSuspendFunction {
            scraperClient.post()
                .uri { uriBuilder: UriBuilder ->
                    uriBuilder
                        .path("/topBlog")
                        .build()
                }
                .bodyValue(
                    GetScrappedStataRequestToScrapperServer.of("Marketing Root Spring Server", keyword)
                )
                .retrieve()
                .onStatus({ it.isError }) { clientResponse ->
                    clientResponse.bodyToMono(String::class.java).flatMap { errorBody ->
                        logger.error { "HTTP Error: ${clientResponse.statusCode()} / Body: $errorBody" }
                        throw RuntimeException("HTTP Error: ${clientResponse.statusCode()} - $errorBody")
                    }
                }
                .awaitBody<ScrappedTopBLoggerStatFromScrapperServer>()
        }
    }

   fun fetchScrappedTop10BloggerStat(
        keyword: String,
        throwable: Throwable
    ): ScrappedTopBLoggerStatFromScrapperServer? {
        return null
    }
}