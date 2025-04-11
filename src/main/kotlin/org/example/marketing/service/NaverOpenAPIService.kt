package org.example.marketing.service

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.example.marketing.config.NaverAdApiProperties
import org.example.marketing.dto.naver.NaverAdApiParameter
import org.example.marketing.dto.naver.NaverOpenApiParameter
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

@Service
class NaverOpenAPIService(
    @Qualifier("naverOpenApiClient") private val openApiClient: WebClient,
    private val adApiProps: NaverAdApiProperties
    ) {
    private val logger = KotlinLogging.logger {}

    @CircuitBreaker(
        name = "naverOpenApiCircuitBreaker",
        fallbackMethod = "fetchBlogFallbackMethod"
    )
    fun fetchBlogData(parameter: NaverOpenApiParameter): Mono<String> {
        return openApiClient.get()
            .uri { uriBuilder ->
                val builder = uriBuilder
                    .path("/v1/search/blog.json")
                    .queryParam("query", parameter.query)

                parameter.display?.let { builder.queryParam("display", it) }
                parameter.start?.let { builder.queryParam("start", it) }
                parameter.sort?.let { builder.queryParam("sort", it) }

                builder.build()
            }
            .retrieve()
            .bodyToMono(String::class.java)
    }

    @CircuitBreaker(
        name = "naverAdApiCircuitBreaker",
        fallbackMethod = "fetchRelKwdStatFallbackMethod"
    )
    fun fetchRelKwdStatData(
        parameter: NaverAdApiParameter
    ): Mono<String> {
        val timeStamp = System.currentTimeMillis().toString()
        val path = "/keywordstool"
        val method = "GET"
        val signature = generateSignature(timeStamp, method, path, adApiProps.secretKey)
        logger.info {signature}

        return WebClient.builder()
            .baseUrl("https://api.searchad.naver.com")
            .defaultHeader("X-Timestamp", timeStamp)
            .defaultHeader("X-API-KEY", adApiProps.apiKey)
            .defaultHeader("X-Customer", adApiProps.customerId)
            .defaultHeader("X-Signature", signature)
            .defaultHeader("Content-Type", "application/json; charset=utf-8")
            .build()
            .get()
            .uri { uriBuilder ->
                uriBuilder.path(path)
                    .queryParam("hintKeywords", parameter.hintKeywords)
                    .queryParam("showDetail", "1")
                    .apply {
                        parameter.event?.let { queryParam("event", it) }
                    }
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

    fun fetchRelKwdStatFallbackMethod(
        parameter: NaverAdApiParameter,
        throwable: Throwable
    ): Mono<String> {
        return Mono.just("naver opan api service doesn't work")
    }

    private fun generateSignature(
        timeStamp: String,
        method: String,
        path: String,
        secretKey: String
    ): String {
        val message = "$timeStamp.$method.$path"
        val mac = Mac.getInstance("HmacSHA256")
        val secretKeySpec = SecretKeySpec(secretKey.toByteArray(Charsets.UTF_8), "HmacSHA256")
        mac.init(secretKeySpec)
        val rawHmac = mac.doFinal(message.toByteArray(Charsets.UTF_8))
        return Base64.getEncoder().encodeToString(rawHmac)
    }
}