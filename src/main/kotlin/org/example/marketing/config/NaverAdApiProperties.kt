package org.example.marketing.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "naver.ad")
data class NaverAdApiProperties(
    var apiKey: String = "",
    var customerId: String = "",
    var secretKey: String = "",
)