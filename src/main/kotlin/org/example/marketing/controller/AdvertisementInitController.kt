package org.example.marketing.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.dto.board.response.AdvertisementInitResponse
import org.example.marketing.dto.board.response.AdvertisementInitWithLikedResponse
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.service.AdvertisementInitService
import org.example.marketing.service.AuthApiService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/init")
class AdvertisementInitController(
    private val advertisementInitService: AdvertisementInitService,
    private val authApiService: AuthApiService,
) {
    private val logger = KotlinLogging.logger {}

    /**
     * Fresh 광고 목록 조회
     * - 비로그인 유저: 좋아요 정보 없이 반환
     * - 로그인 유저: 좋아요 정보 포함하여 반환
     */
    @GetMapping("/advertisement/fresh")
    suspend fun getFreshInit(
        @RequestHeader("Authorization", required = false) authorization: String?
    ): ResponseEntity<*> {
        logger.info { "GET /init/advertisement/fresh - authorization=${authorization?.take(20) ?: "none"}" }

        // 비로그인 유저
        if (authorization.isNullOrBlank()) {
            val result = advertisementInitService.findInitFreshAdWithThumbnail()
            return ResponseEntity.ok().body(
                AdvertisementInitResponse.of(
                    frontErrorCode = FrontErrorCode.OK.code,
                    errorMessage = FrontErrorCode.OK.message,
                    result = result
                )
            )
        }

        // 로그인 유저
        val extractedUser = authApiService.validateInfluencer(authorization)
        val userType = extractedUser.userType

        val result = advertisementInitService.findInitFreshAdWithThumbnailAndLiked(extractedUser.userId)

        return ResponseEntity.ok().body(
            AdvertisementInitWithLikedResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                result = result
            )
        )
    }

    /**
     * Deadline 광고 목록 조회
     * - 비로그인 유저: 좋아요 정보 없이 반환
     * - 로그인 유저: 좋아요 정보 포함하여 반환
     */
    @GetMapping("/advertisement/deadline")
    suspend fun getDeadlineInit(
        @RequestHeader("Authorization", required = false) authorization: String?
    ): ResponseEntity<*> {
        logger.info { "GET /init/advertisement/deadline - authorization=${authorization?.take(20) ?: "none"}" }

        // 비로그인 유저
        if (authorization.isNullOrBlank()) {
            val result = advertisementInitService.findDeadlineFreshAdWithThumbnail()
            return ResponseEntity.ok().body(
                AdvertisementInitResponse.of(
                    frontErrorCode = FrontErrorCode.OK.code,
                    errorMessage = FrontErrorCode.OK.message,
                    result = result
                )
            )
        }

        // 로그인 유저
        val extractedUser = authApiService.validateInfluencer(authorization)
        val result = advertisementInitService.findDeadlineAdWithThumbnailAndLiked(extractedUser.userId)

        return ResponseEntity.ok().body(
            AdvertisementInitWithLikedResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                result = result
            )
        )
    }

    /**
     * Hot 광고 목록 조회 (지원자 수 기준 내림차순)
     * - 비로그인 유저: 좋아요 정보 없이 반환
     * - 로그인 유저: 좋아요 정보 포함하여 반환
     */
    @GetMapping("/advertisement/hot")
    suspend fun getHotInit(
        @RequestHeader("Authorization", required = false) authorization: String?
    ): ResponseEntity<*> {
        logger.info { "GET /init/advertisement/hot - authorization=${authorization?.take(20) ?: "none"}" }

        // 비로그인 유저
        if (authorization.isNullOrBlank()) {
            val result = advertisementInitService.findInitHotAdWithThumbnail()
            return ResponseEntity.ok().body(
                AdvertisementInitResponse.of(
                    frontErrorCode = FrontErrorCode.OK.code,
                    errorMessage = FrontErrorCode.OK.message,
                    result = result
                )
            )
        }

        // 로그인 유저
        val extractedUser = authApiService.validateInfluencer(authorization)
        val result = advertisementInitService.findInitHotAdWithThumbnailAndLiked(extractedUser.userId)

        return ResponseEntity.ok().body(
            AdvertisementInitWithLikedResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                result = result
            )
        )
    }
}
