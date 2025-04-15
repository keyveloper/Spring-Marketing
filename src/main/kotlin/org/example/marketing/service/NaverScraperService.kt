package org.example.marketing.service

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import io.github.resilience4j.kotlin.circuitbreaker.executeSuspendFunction
import org.example.marketing.dto.keyword.GetScrappedDataRequest
import org.example.marketing.dto.keyword.ScrappedDataFromScrapperServer
import org.example.marketing.dto.keyword.ScrappedResponseFromSScrapperServer
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.util.UriBuilder
import kotlin.math.log

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
    suspend fun fetchScrapedData(keywords: List<String>): List<ScrappedDataFromScrapperServer> {
        return try {
            circuitBreaker.executeSuspendFunction {
                scraperClient.post()
                    .uri { uriBuilder: UriBuilder ->
                        uriBuilder
                            .path("/run")
                            .build()
                    }
                    .bodyValue(
                        GetScrappedDataRequest.of(client = "Marketing Root Spring Server", keywords = keywords)
                    )
                    .retrieve()
                    .onStatus({ it.isError }) { clientResponse ->
                        clientResponse.bodyToMono(String::class.java).flatMap { errorBody ->
                            logger.error { "HTTP Error: ${clientResponse.statusCode()} / Body: $errorBody" }
                            throw RuntimeException("HTTP Error: ${clientResponse.statusCode()} - $errorBody")
                        }
                    }
                    .awaitBody<ScrappedResponseFromSScrapperServer>()
                    .scrappedData
            }
        } catch (ex: Throwable) {
            logger.error { "fetchScrapedData circuit error: ${ex.message}" }
            return emptyList()
        }
    }

   fun fetchScrapedFallbackMethod(
        keyword: List<String>,
        throwable: Throwable
    ): List<ScrappedDataFromScrapperServer> {
        return listOf()
    }
}