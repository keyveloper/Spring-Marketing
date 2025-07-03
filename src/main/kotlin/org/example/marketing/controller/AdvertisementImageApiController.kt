package org.example.marketing.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.dto.board.request.ConnectAdvertisementIdRequest
import org.example.marketing.dto.board.request.UploadAdvertisementImageRequestFromClient
import org.example.marketing.dto.board.response.FetchAdvertisementImageResponseToClient
import org.example.marketing.dto.board.response.UploadAdvertisementImageResponseToClient
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.service.AdvertisementImageApiService
import org.example.marketing.service.AuthApiService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class AdvertisementImageApiController(
    private val advertisementImageApiService: AdvertisementImageApiService,
    private val authApiService: AuthApiService,
) {
    val logger = KotlinLogging.logger {}

    @PostMapping("/image/advertisement")
    suspend fun uploadImage(
        @RequestHeader("Authorization") authorization: String,
        @RequestPart("meta") meta: UploadAdvertisementImageRequestFromClient,
        @RequestPart("file") file: MultipartFile
    ): ResponseEntity<UploadAdvertisementImageResponseToClient> {
        logger.info { "Validating advertiser authorization for advertisement creation" }
        logger.info { "Received Authorization header: ${authorization.take(50)}..." }  // 토큰 앞부분만 로깅
        val extractedAdvertiser = authApiService.validateAdvertiser(authorization)
        logger.info { "Advertiser validated: userId=${extractedAdvertiser.userId}," +
                " email=${extractedAdvertiser.email}" }

        // Capture authentication before async execution
        val userId = extractedAdvertiser.userId
        val draftId = meta.advertisementDraftId
        val isThumbnail = meta.isThumbnail

        val result = advertisementImageApiService.uploadToImageServer(
            userId =userId,
            advertisementDraftId = draftId,
            isThumbnail = isThumbnail,
            file = file
        )

        /***
         TODO: Should Add Exception when userType equals INFLUENCER....! 👉 add validation
        ***/

        return ResponseEntity.ok().body(
            UploadAdvertisementImageResponseToClient(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                result = result
            )
        )
    }

    @GetMapping("/open/image/advertisement/{adId}")
    suspend fun fetchImageByAdId(
        @PathVariable("adId") adId: Long,
    ): ResponseEntity<FetchAdvertisementImageResponseToClient> {
        val result = advertisementImageApiService.fetchImageByAdvertisementId(adId)

        return ResponseEntity.ok().body(
            FetchAdvertisementImageResponseToClient.of(
                result = result,
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
            )
        )
    }

    /**
     * Draft의 이미지들을 실제 Advertisement와 연결하는 엔드포인트
     *
     * @param request draftId와 advertisementId를 포함한 요청
     * @return 연결 결과 (업데이트된 행 수와 연결된 S3 키 목록)
     */
    @PostMapping("/image/advertisement/connect")
    suspend fun connectAdvertisementImages(
        @RequestBody request: ConnectAdvertisementIdRequest
    ): ResponseEntity<Map<String, Any>> {
        logger.info { "Testing connect advertisement images: draftId=${request.draftId}, advertisementId=${request.advertisementId}" }

        val result = advertisementImageApiService.connectAdvertisementToImageServer(
            draftId = request.draftId,
            advertisementId = request.advertisementId
        )

        logger.info { "Connection result: updatedRow=${result.updatedRow}, connectedKeys=${result.connectedS3BucketKeys.size}" }

        return ResponseEntity.ok().body(
            mapOf(
                "frontErrorCode" to FrontErrorCode.OK.code,
                "errorMessage" to FrontErrorCode.OK.message,
                "result" to result
            )
        )
    }
}