package org.example.marketing.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
data class NaverDatalabProperties(
    var clientId: String = "",
    var clientSecret: String = "",
)