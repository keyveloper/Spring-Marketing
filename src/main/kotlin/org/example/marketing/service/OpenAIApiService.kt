package org.example.marketing.service

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.springframework.stereotype.Service

@Service
class OpenAIApiService(

) {

    @CircuitBreaker
}