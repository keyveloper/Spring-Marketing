package org.example.marketing

import org.example.marketing.config.NaverDatalabProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
class MarketingApplication

fun main(args: Array<String>) {
    runApplication<MarketingApplication>(*args)
}
