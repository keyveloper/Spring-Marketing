package org.example.marketing.config

import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component

@Component
class CheckProperties(val props: NaverAdApiProperties) {
    @PostConstruct
    fun init() {
        println(">> secretKey = '${props.secretKey}'")
    }
}