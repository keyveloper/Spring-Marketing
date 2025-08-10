package org.example.marketing.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebCorsConfig: WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins(
                "http://localhost:5173",  // Vite 기본 포트
                "http://localhost:5174",   // 추가 포트
                "http://marketing-web-app.s3-website.ap-northeast-2.amazonaws.com", //s3 web hosting,
                "https://d1gyb5hcofe8jv.cloudfront.net",
                "http://localhost:80",
            )
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
            .allowCredentials(true)
            .allowedHeaders("*")
            .exposedHeaders("Authorization")
            .maxAge(3000)
    }
}
