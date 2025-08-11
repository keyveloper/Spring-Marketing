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
    @Bean("imageApiServerClient")
    fun imageApiServerWebClient(): WebClient {
        val timeout = Duration.ofSeconds(30)

        val httpClient = HttpClient.create()
            .responseTimeout(timeout)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10_000)
            .doOnConnected { conn ->
                conn.addHandlerLast(ReadTimeoutHandler(timeout.seconds.toInt()))
                conn.addHandlerLast(WriteTimeoutHandler(timeout.seconds.toInt()))
            }

        return WebClient.builder()
            .clientConnector(ReactorClientHttpConnector(httpClient))
            .baseUrl("http://localhost:8081") // marketing-api-server base URL
            .defaultHeader("Content-Type", "application/json")
            .build()
    }

    @Bean("authApiServerClient")
    fun authApiServerWebClient(): WebClient {
        val timeout = Duration.ofSeconds(30)

        val httpClient = HttpClient.create()
            .responseTimeout(timeout)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10_000)
            .doOnConnected { conn ->
                conn.addHandlerLast(ReadTimeoutHandler(timeout.seconds.toInt()))
                conn.addHandlerLast(WriteTimeoutHandler(timeout.seconds.toInt()))
            }

        return WebClient.builder()
            .clientConnector(ReactorClientHttpConnector(httpClient))
            .baseUrl("http://localhost:8088") // Auth API Server base URL
            .defaultHeader("Content-Type", "application/json")
            .build()
    }

    @Bean("userProfileApiClient")
    fun userProfileApiWebClient(): WebClient {
        val timeout = Duration.ofSeconds(30)

        val httpClient = HttpClient.create()
            .responseTimeout(timeout)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10_000)
            .doOnConnected { conn ->
                conn.addHandlerLast(ReadTimeoutHandler(timeout.seconds.toInt()))
                conn.addHandlerLast(WriteTimeoutHandler(timeout.seconds.toInt()))
            }

        return WebClient.builder()
            .clientConnector(ReactorClientHttpConnector(httpClient))
            .baseUrl("http://localhost:8082") // User Profile API Server base URL
            .defaultHeader("Content-Type", "application/json")
            .build()
    }

    @Bean("notiApiServerClient")
    fun notiApiServerWebClient(): WebClient {
        val timeout = Duration.ofSeconds(30)

        val httpClient = HttpClient.create()
            .responseTimeout(timeout)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10_000)
            .doOnConnected { conn ->
                conn.addHandlerLast(ReadTimeoutHandler(timeout.seconds.toInt()))
                conn.addHandlerLast(WriteTimeoutHandler(timeout.seconds.toInt()))
            }

        return WebClient.builder()
            .clientConnector(ReactorClientHttpConnector(httpClient))
            .baseUrl("http://localhost:8085") // Noti API Server base URL
            .defaultHeader("Content-Type", "application/json")
            .build()
    }

    @Bean("timelineApiServerClient")
    fun timelineApiServerWebClient(): WebClient {
        val timeout = Duration.ofSeconds(30)

        val httpClient = HttpClient.create()
            .responseTimeout(timeout)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10_000)
            .doOnConnected { conn ->
                conn.addHandlerLast(ReadTimeoutHandler(timeout.seconds.toInt()))
                conn.addHandlerLast(WriteTimeoutHandler(timeout.seconds.toInt()))
            }

        return WebClient.builder()
            .clientConnector(ReactorClientHttpConnector(httpClient))
            .baseUrl("http://localhost:8086") // Timeline API Server base URL
            .defaultHeader("Content-Type", "application/json")
            .build()
    }

    @Bean("followApiServerClient")
    fun followApiServerWebClient(): WebClient {
        val timeout = Duration.ofSeconds(30)

        val httpClient = HttpClient.create()
            .responseTimeout(timeout)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10_000)
            .doOnConnected { conn ->
                conn.addHandlerLast(ReadTimeoutHandler(timeout.seconds.toInt()))
                conn.addHandlerLast(WriteTimeoutHandler(timeout.seconds.toInt()))
            }

        return WebClient.builder()
            .clientConnector(ReactorClientHttpConnector(httpClient))
            .baseUrl("http://localhost:8084") // Follow API Server base URL
            .defaultHeader("Content-Type", "application/json")
            .build()
    }

    @Bean("likeApiServerClient")
    fun likeApiServerWebClient(): WebClient {
        val timeout = Duration.ofSeconds(30)

        val httpClient = HttpClient.create()
            .responseTimeout(timeout)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10_000)
            .doOnConnected { conn ->
                conn.addHandlerLast(ReadTimeoutHandler(timeout.seconds.toInt()))
                conn.addHandlerLast(WriteTimeoutHandler(timeout.seconds.toInt()))
            }

        return WebClient.builder()
            .clientConnector(ReactorClientHttpConnector(httpClient))
            .baseUrl("http://localhost:8083") // Like API Server base URL
            .defaultHeader("Content-Type", "application/json")
            .build()
    }
}
