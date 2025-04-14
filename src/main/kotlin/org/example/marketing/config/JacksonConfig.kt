package org.example.marketing.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JacksonConfig {
    @Bean
    fun objectMapper(): ObjectMapper {
        val module = SimpleModule().apply {
            addDeserializer(Int::class.java, SafeIntDeserializer())
        }

        return ObjectMapper()
            .registerModule(KotlinModule.Builder().build())
            .registerModule(module)
    }
}