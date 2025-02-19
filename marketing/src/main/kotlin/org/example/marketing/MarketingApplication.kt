package org.example.marketing

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class MarketingApplication

fun main(args: Array<String>) {
    runApplication<MarketingApplication>(*args)
}
