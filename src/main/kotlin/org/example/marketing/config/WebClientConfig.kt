package org.example.marketing.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig {

    @Bean("naverOpenApiClient")
    fun openNaverApiWebclient(props: NaverDatalabProperties): WebClient {
        return WebClient.builder()
            .baseUrl("https://openapi.naver.com")
            .defaultHeaders { headers ->
                headers["X-Naver-Client-Id"] = props.clientId
                headers["X-Naver-Client-Secret"] = props.clientSecret
            }
            .build()
    }
}