package org.example.marketing.service

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.example.marketing.dto.naver.NaverOpenApiParameter
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class NaverOpenAPIService(
    @Qualifier("naverOpenApiClient") private val webClient: WebClient,
    private val circuitBreakerRegistry: CircuitBreakerRegistry,
) {
    private val circuitBreaker = circuitBreakerRegistry.circuitBreaker("naverOpenApiCircuitBreaker")
    private val logger = KotlinLogging.logger {}

    @CircuitBreaker(
        name = "naverOpenApiCircuitBreaker",
        fallbackMethod = "fetchBlogFallbackMethod"
    )
    fun fetchBlogData(parameter: NaverOpenApiParameter): Mono<String> {
        return webClient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .path("/v1/serach/blog.json")
                    .queryParam("query", parameter.query)
                    .queryParam("display", parameter.display)
                    .queryParam("start", parameter.start)
                    .queryParam("sort", parameter.sort)
                    .build()
            }
            .retrieve()
            .bodyToMono(String::class.java)
    }


    fun fetchBlogFallbackMethod(
        parameter: NaverOpenApiParameter,
        throwable: Throwable
    ): Mono<String> {
        return Mono.just("naver opan api service doesn't work")
    }
}