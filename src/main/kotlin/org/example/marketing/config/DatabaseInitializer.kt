package org.example.marketing.config

import jakarta.annotation.PostConstruct
import org.jetbrains.exposed.sql.Database
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class DatabaseInitializer(
    @Value("\${spring.datasource.url}") private val url: String,
    @Value("\${spring.datasource.username}") private val username: String,
    @Value("\${spring.datasource.password}") private val password: String,
    @Value("\${spring.datasource.driver-class-name}") private val driver: String,
    ) {

    @PostConstruct
    fun init() {
        Database.connect(
            url = url,
            user = username,
            password = password,
            driver = driver
        )
    }
}