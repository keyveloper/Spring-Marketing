package org.example.marketing.config

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

@Configuration
class WebClientConfig {
    @Bean("naverOpenApiClient")
    fun naverOpenApiWebclient(props: NaverOpenApiProperties): WebClient {
        return WebClient.builder()
            .baseUrl("https://openapi.naver.com")
            .defaultHeaders { headers ->
                headers["X-Naver-Client-Id"] = props.clientId
                headers["X-Naver-Client-Secret"] = props.clientSecret
            }
            .build()
    }
}
