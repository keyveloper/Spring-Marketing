package org.example.marketing.config

import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import java.time.Duration

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

    @Bean("naverScraperClient")
    fun naverScraperWebClient(): WebClient {
        val timeout = Duration.ofSeconds(300)

        val httpClient = HttpClient.create()
            .responseTimeout(timeout)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30_000)
            .doOnConnected { conn ->
                conn.addHandlerLast(ReadTimeoutHandler(timeout.seconds.toInt()))
                conn.addHandlerLast(WriteTimeoutHandler(timeout.seconds.toInt()))
            }

        return WebClient.builder()
            .clientConnector(ReactorClientHttpConnector(httpClient))
            .baseUrl("http://localhost:3000/scrapper") // Customize if needed
            .build()
    }
}
