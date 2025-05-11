package org.example.marketing.service

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import io.github.resilience4j.kotlin.circuitbreaker.executeSuspendFunction
import org.example.marketing.config.NaverAdApiProperties
import org.example.marketing.dto.keyword.NaverAdApiParameter
import org.example.marketing.dto.keyword.RelatedKeywordStat
import org.example.marketing.dto.keyword.RelatedKeywordResponseFromNaverAdServer
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

@Service
class NaverOpenAPIService(
    private val adApiProps: NaverAdApiProperties,
    private val circuitBreakerRegistry: CircuitBreakerRegistry

) {
    private val logger = KotlinLogging.logger {}
    private val circuitBreaker = circuitBreakerRegistry.circuitBreaker("naverAdApiCircuitBreaker")

    @CircuitBreaker(
        name = "naverAdApiCircuitBreaker",
    )
    suspend fun fetchRelatedKeyword(
        parameter: NaverAdApiParameter
    ): List<RelatedKeywordStat> {
        val timeStamp = System.currentTimeMillis().toString()
        val path = "/keywordstool"
        val method = "GET"
        val signature =
            generateSignatureForNaverAdApi(timeStamp, method, path, adApiProps.secretKey)

        val client = WebClient.builder()
            .baseUrl("https://api.searchad.naver.com")
            .defaultHeader("X-Timestamp", timeStamp)
            .defaultHeader("X-API-KEY", adApiProps.apiKey)
            .defaultHeader("X-Customer", adApiProps.customerId)
            .defaultHeader("X-Signature", signature)
            .defaultHeader("Content-Type", "application/json; charset=utf-8")
            .build()

        logger.info { "hint keyword: ${parameter.hintKeyword}" }
        return try {
            circuitBreaker.executeSuspendFunction {
                client.get()
                   .uri { uriBuilder ->
                       uriBuilder.path(path)
                           .queryParam(
                               "hintKeywords",
                               parameter.hintKeyword.replace("\\s".toRegex(), "")
                           )
                           .queryParam("showDetail", "1")
                           .apply {
                               parameter.event?.let { queryParam("event", it) }
                           }
                           .build()
                   }
                   .headers { h -> h.forEach { name, values -> println("$name: ${values.joinToString()}") } }
                   .retrieve()
                   .awaitBody<RelatedKeywordResponseFromNaverAdServer>()
                   .keywordList
           }
       } catch (ex: Throwable) {
           logger.error { "fetch related circuit error: ${ex.message}" }
           return emptyList()
       }
    }



    fun fetchRelatedKeywordFallbackMethod(
        parameter: NaverAdApiParameter,
        throwable: Throwable
    ): List<RelatedKeywordStat> {
        return listOf()
    }

    private fun generateSignatureForNaverAdApi(
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