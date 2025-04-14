package org.example.marketing.service

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.example.marketing.config.NaverAdApiProperties
import org.example.marketing.dto.keyword.RelatedKeywordFromNaverAdServer
import org.example.marketing.dto.keyword.NaverAdApiParameter
import org.example.marketing.dto.keyword.RelatedKeywordResponseFromNaverAdServer
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import reactor.core.publisher.Mono
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

@Service
class NaverOpenAPIService(
    private val adApiProps: NaverAdApiProperties,
) {
    private val logger = KotlinLogging.logger {}


    @CircuitBreaker(
        name = "naverAdApiCircuitBreaker",
        fallbackMethod = "fetchRelatedKeywordFallback"
    )
    suspend fun fetchRelatedKeyword(
        parameter: NaverAdApiParameter
    ): List<RelatedKeywordFromNaverAdServer> {
        val timeStamp = System.currentTimeMillis().toString()
        val path = "/keywordstool"
        val method = "GET"
        val signature =
            generateSignatureForNaverAdApi(timeStamp, method, path, adApiProps.secretKey)
        logger.info {signature}

        val client = WebClient.builder()
            .baseUrl("https://api.searchad.naver.com")
            .defaultHeader("X-Timestamp", timeStamp)
            .defaultHeader("X-API-KEY", adApiProps.apiKey)
            .defaultHeader("X-Customer", adApiProps.customerId)
            .defaultHeader("X-Signature", signature)
            .defaultHeader("Content-Type", "application/json; charset=utf-8")
            .build()

       return client.get()
           .uri { uriBuilder ->
                uriBuilder.path(path)
                    .queryParam("hintKeywords", parameter.hintKeyword)
                    .queryParam("showDetail", "1")
                    .apply {
                        parameter.event?.let { queryParam("event", it) }
                    }
                    .build()
            }
           .retrieve()
           .awaitBody<RelatedKeywordResponseFromNaverAdServer>()
           .keywordList
    }



    fun fetchRelKwdStatFallbackMethod(
        parameter: NaverAdApiParameter,
        throwable: Throwable
    ): List<RelatedKeywordFromNaverAdServer> {
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