package org.example.marketing.service

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.example.marketing.config.NaverDatalabProperties
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class KeywordService(
    @Qualifier("naverOpenApiClient") private val webClient: WebClient,
    private val circuitBreakerRegistry: CircuitBreakerRegistry,
    ) {
    private val circuitBreaker = circuitBreakerRegistry.circuitBreaker("keywordCircuitBreaker")
    private val logger = KotlinLogging.logger {}

    @CircuitBreaker(
        name = "notification"
    )
}