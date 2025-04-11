package org.example.marketing.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "naver.datalab")
data class NaverOpenApiProperties(
    var clientId: String = "",
    var clientSecret: String = "",
)