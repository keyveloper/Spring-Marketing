package org.example.marketing.service

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import io.github.resilience4j.kotlin.circuitbreaker.executeSuspendFunction
import org.example.marketing.dto.user.response.ExtractUserResponseFromServer
import org.example.marketing.dto.user.response.ExtractedUserFromToken
import org.example.marketing.enums.MSAServiceErrorCode
import org.example.marketing.exception.MSAErrorException
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

/**
 * Auth API Server와 통신하는 서비스
 * JWT 토큰 검증 및 사용자 정보 추출 기능을 제공합니다.
 */
@Service
class AuthApiService(
    @Qualifier("authApiServerClient") private val authApiServerClient: WebClient,
    private val circuitBreakerRegistry: CircuitBreakerRegistry
) {
    private val logger = KotlinLogging.logger {}
    private val circuitBreaker = circuitBreakerRegistry.circuitBreaker("authApiCircuitBreaker")

    /**
     * Influencer 토큰 검증 및 정보 추출
     *
     * @param token JWT 토큰 (Bearer prefix 포함)
     * @return ExtractedUserFromToken 사용자 정보
     * @throws MSAErrorException 검증 실패 또는 유저 타입 불일치 시
     */
    suspend fun validateInfluencer(token: String): ExtractedUserFromToken {
        logger.info { "Validating influencer token with auth-api-server" }

        return try {
            circuitBreaker.executeSuspendFunction {
                val response = authApiServerClient.get()
                    .uri("/api/auth/validate/influencer")
                    .header("Authorization", token)
                    .retrieve()
                    .awaitBody<ExtractUserResponseFromServer>()

                logger.info { "Received response from auth-api-server: msaServiceErrorCode=" +
                        "${response.msaServiceErrorCode}, httpStatus=${response.httpStatus}" }

                when (response.msaServiceErrorCode) {
                    MSAServiceErrorCode.OK -> {
                        val user = response.extractedUserFromToken
                            ?: throw MSAErrorException(
                                logics = "AuthApiService - validateInfluencer: extractedUserFromToken is null",
                                message = "Failed to extract user information from token"
                            )

                        logger.info { "Successfully validated influencer: userId=${user.userId}, " +
                                "email=${user.email}, userType=${user.userType}" }

                        user
                    }
                    else -> {
                        logger.error { "Influencer validation failed with msaServiceErrorCode=" +
                                "${response.msaServiceErrorCode}, errorMessage=${response.errorMessage}, " +
                                "logics=${response.logics}" }
                        throw MSAErrorException(
                            logics = "AuthApiService - validateInfluencer: ${response.logics}",
                            message = response.errorMessage ?: "Influencer validation failed"
                        )
                    }
                }
            }
        } catch (ex: Throwable) {
            logger.error { "Failed to validate influencer token: ${ex.message}" }
            throw ex
        }
    }

    /**
     * Advertiser 토큰 검증 및 정보 추출
     *
     * @param token JWT 토큰 (Bearer prefix 포함)
     * @return ExtractedUserFromToken 사용자 정보
     * @throws MSAErrorException 검증 실패 또는 유저 타입 불일치 시
     */
    suspend fun validateAdvertiser(token: String): ExtractedUserFromToken {
        logger.info { "Validating advertiser token with auth-api-server" }

        return try {
            circuitBreaker.executeSuspendFunction {
                val response = authApiServerClient.get()
                    .uri("/api/auth/validate/advertiser")
                    .header("Authorization", token)
                    .retrieve()
                    .awaitBody<ExtractUserResponseFromServer>()

                logger.info { "Received response from auth-api-server: msaServiceErrorCode=" +
                        "${response.msaServiceErrorCode}, httpStatus=${response.httpStatus}" }

                when (response.msaServiceErrorCode) {
                    MSAServiceErrorCode.OK -> {
                        val user = response.extractedUserFromToken
                            ?: throw MSAErrorException(
                                logics = "AuthApiService - validateAdvertiser: extractedUserFromToken is null",
                                message = "Failed to extract user information from token"
                            )

                        logger.info { "Successfully validated advertiser: userId=${user.userId}, " +
                                "email=${user.email}, userType=${user.userType}" }

                        user
                    }
                    else -> {
                        logger.error { "Advertiser validation failed with msaServiceErrorCode=" +
                                "${response.msaServiceErrorCode}, errorMessage=${response.errorMessage}, " +
                                "logics=${response.logics}" }
                        throw MSAErrorException(
                            logics = "AuthApiService - validateAdvertiser: ${response.logics}",
                            message = response.errorMessage ?: "Advertiser validation failed"
                        )
                    }
                }
            }
        } catch (ex: Throwable) {
            logger.error { "Failed to validate advertiser token: ${ex.message}" }
            throw ex
        }
    }

    /**
     * Influencer 검증 실패 시 Fallback 메서드
     */
    suspend fun validateInfluencerFallback(
        token: String,
        throwable: Throwable
    ): ExtractedUserFromToken {
        logger.error { "Circuit breaker fallback triggered for validateInfluencer: ${throwable.message}" }
        logger.error { "Failed request details - token: ${token.take(20)}..." }
        throw MSAErrorException(
            logics = "AuthApiService - validateInfluencer fallback",
            message = "Failed to validate influencer token: ${throwable.message}"
        )
    }

    /**
     * Advertiser 검증 실패 시 Fallback 메서드
     */
    suspend fun validateAdvertiserFallback(
        token: String,
        throwable: Throwable
    ): ExtractedUserFromToken {
        logger.error { "Circuit breaker fallback triggered for validateAdvertiser: ${throwable.message}" }
        logger.error { "Failed request details - token: ${token.take(20)}..." }
        throw MSAErrorException(
            logics = "AuthApiService - validateAdvertiser fallback",
            message = "Failed to validate advertiser token: ${throwable.message}"
        )
    }
}