package org.example.marketing.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.validation.Valid
import org.example.marketing.dto.board.request.MakeNewAdvertisementGeneralRequest
import org.example.marketing.dto.board.request.UpdateAdvertisementRequest
import org.example.marketing.dto.board.response.GetAdvertisementResponse
import org.example.marketing.dto.board.response.MakeNewAdvertisementGeneralResponse
import org.example.marketing.dto.board.response.UpdateAdvertisementResponse
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.service.AdvertisementGeneralService
import org.example.marketing.service.AuthApiService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class AdvertisementCRUDController( // only for general advertisement
    private val advertisementService: AdvertisementGeneralService,
    private val authApiService: AuthApiService
) {
    private val logger = KotlinLogging.logger {}

    @PostMapping("/advertisement/general")
    suspend fun save(
        @RequestHeader("Authorization") authorization: String,
        @Valid @RequestBody request: MakeNewAdvertisementGeneralRequest
    ): ResponseEntity<MakeNewAdvertisementGeneralResponse> {
        // 광고주 권한 검증
        logger.info { "Validating advertiser authorization for advertisement creation" }
        logger.info { "Received Authorization header: ${authorization.take(50)}..." }  // 토큰 앞부분만 로깅
        val extractedAdvertiser = authApiService.validateAdvertiser(authorization)
        logger.info { "Advertiser validated: userId=${extractedAdvertiser.userId}, email=${extractedAdvertiser.email}" }
        val extractedAdvertiserId = extractedAdvertiser.userId
        // 검증된 advertiser 정보로 광고 생성
        val result = advertisementService.save(extractedAdvertiserId, request)
        return ResponseEntity.ok().body(
            MakeNewAdvertisementGeneralResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                result = result
            )
        )
    }


    @GetMapping("/advertisement/{targetId}")
    suspend fun getById(
        @PathVariable targetId: Long
    ): ResponseEntity<GetAdvertisementResponse> {
        val result = advertisementService.findByIdWithCategoriesAndImages(targetId)
        return ResponseEntity.ok().body(
            GetAdvertisementResponse.of(
                result = result,
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
            )
        )
    }

    @PostMapping("/advertisement/general/update")
    fun update(
        @Valid @RequestBody request: UpdateAdvertisementRequest
    ): ResponseEntity<UpdateAdvertisementResponse> {
        return ResponseEntity.ok().body(
            UpdateAdvertisementResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                updateId = advertisementService.update(request)
            )
        )
    }

}