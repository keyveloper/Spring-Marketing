package org.example.marketing.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.dto.user.response.ExtractedUserFromToken
import org.example.marketing.service.AuthApiService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * AuthApiService를 테스트하기 위한 컨트롤러
 * JWT 토큰 검증 기능을 테스트합니다.
 */
@RestController
@RequestMapping("/test/auth")
class AuthTestController(
    private val authApiService: AuthApiService
) {
    private val logger = KotlinLogging.logger {}

    /**
     * Influencer 토큰 검증 테스트
     *
     * @param authorization Authorization 헤더 (Bearer 토큰)
     * @return ExtractedUserFromToken 사용자 정보
     */
    @GetMapping("/validate/influencer")
    suspend fun testValidateInfluencer(
        @RequestHeader("Authorization") authorization: String
    ): ResponseEntity<ExtractedUserFromToken> {
        logger.info { "Testing influencer validation with token" }

        return try {
            val user = authApiService.validateInfluencer(authorization)
            logger.info { "Successfully validated influencer: userId=${user.userId}, userType=${user.userType}" }
            ResponseEntity.ok(user)
        } catch (e: Exception) {
            logger.error { "Failed to validate influencer: ${e.message}" }
            throw e
        }
    }

    /**
     * Advertiser 토큰 검증 테스트
     *
     * @param authorization Authorization 헤더 (Bearer 토큰)
     * @return ExtractedUserFromToken 사용자 정보
     */
    @GetMapping("/validate/advertiser")
    suspend fun testValidateAdvertiser(
        @RequestHeader("Authorization") authorization: String
    ): ResponseEntity<ExtractedUserFromToken> {
        logger.info { "Testing advertiser validation with token" }

        return try {
            val user = authApiService.validateAdvertiser(authorization)
            logger.info { "Successfully validated advertiser: userId=${user.userId}, userType=${user.userType}" }
            ResponseEntity.ok(user)
        } catch (e: Exception) {
            logger.error { "Failed to validate advertiser: ${e.message}" }
            throw e
        }
    }

    /**
     * 헬스 체크 엔드포인트
     */
    @GetMapping("/health")
    fun health(): ResponseEntity<Map<String, String>> {
        return ResponseEntity.ok(
            mapOf(
                "status" to "UP",
                "service" to "auth-test-controller"
            )
        )
    }
}