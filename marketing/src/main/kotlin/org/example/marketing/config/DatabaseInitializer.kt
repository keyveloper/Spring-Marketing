package org.example.marketing.config

import jakarta.annotation.PostConstruct
import org.jetbrains.exposed.sql.Database
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class DatabaseInitializer(
    @Value("\${Spring.datasource.url}") private val url: String,
    @Value("\${Spring.datasource.username}") private val username: String,
    @Value("\${Spring.datasource.password}") private val password: String,
    @Value("\${Spring.datasource.driver-class-name}") private val driver: String,
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