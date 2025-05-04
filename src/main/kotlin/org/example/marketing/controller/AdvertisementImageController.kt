package org.example.marketing.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.validation.Valid
import org.apache.tika.metadata.HttpHeaders
import org.example.marketing.domain.user.AdvertiserPrincipal
import org.example.marketing.dto.board.request.MakeNewAdvertisementImageRequest
import org.example.marketing.dto.board.request.SetAdvertisementThumbnailRequest
import org.example.marketing.dto.board.response.*
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.service.AdvertisementImageDslService
import org.example.marketing.service.AdvertisementImageService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
class AdvertisementImageController(
    private val advertisementImageService: AdvertisementImageService,
    private val advertisementImageDslService: AdvertisementImageDslService
) {
    val logger = KotlinLogging.logger {}
    //  ✅ check legal approach
    @PostMapping(
        "/advertisement/image",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE]
    )
    fun save(
        @AuthenticationPrincipal advertiserPrincipal: AdvertiserPrincipal,
        @RequestPart("meta") meta: MakeNewAdvertisementImageRequest,
        @RequestPart("file") file: MultipartFile
    ): ResponseEntity<MakeNewAdvertisementImageResponse> {
        return ResponseEntity.ok().body(
            MakeNewAdvertisementImageResponse.of(
                FrontErrorCode.OK.code,
                FrontErrorCode.OK.message,
                advertisementImageService.save(advertiserPrincipal.userId, meta, file)
            )
        )
    }

    @DeleteMapping("/advertisement/image/{metaId}")
    fun deleteById(
        @AuthenticationPrincipal advertiserPrincipal: AdvertiserPrincipal,
        @PathVariable metaId: Long
    ): ResponseEntity<DeleteAdImageResponse> {
        advertisementImageService.deleteById(
            advertiserPrincipal.userId,
            metaId
        )
        return ResponseEntity.ok().body(
            DeleteAdImageResponse(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message
            )
        )
    }

    // 🤔 should check every api call ???
    @PostMapping("/advertisement/image/thumbnail")
    fun setThumbNailById(
        @AuthenticationPrincipal advertiserPrincipal: AdvertiserPrincipal,
        @RequestBody request: SetAdvertisementThumbnailRequest,
    )
    :ResponseEntity<SetAdvertisementThumbnailResponse> {

        advertisementImageService.setThumbnailImage(advertiserPrincipal.userId, request)
        return ResponseEntity.ok().body(
            SetAdvertisementThumbnailResponse.of(
                FrontErrorCode.OK.code,
                FrontErrorCode.OK.message
            )
        )
    }

    @PostMapping("/advertisement/image/publisher/{draftId}")
    fun setAdvertisementId(
        @Valid @PathVariable draftId: Long,
    ): ResponseEntity<SetImageAdvertisementIdResponse> {
        advertisementImageDslService.setImageAdvertisementIdByDraftId(draftId)
        return ResponseEntity.ok().body(
            SetImageAdvertisementIdResponse.of(
                FrontErrorCode.OK.code,
                FrontErrorCode.OK.message,
            )
        )
    }

    @GetMapping("/open/advertisement/image/thumbnail/{advertisementId}")
    fun getThumbnailUrlByAdvertisementId(
        @PathVariable("advertisementId") advertisementId: Long
    ): ResponseEntity<GetAdThumbnailUrlResponse> {
        return ResponseEntity.ok().body(
            GetAdThumbnailUrlResponse.of(
                FrontErrorCode.OK.code,
                FrontErrorCode.OK.message,
                advertisementImageService.findThumbnailUrlByAdvertisementId(advertisementId)
            )
        )
    }

    @GetMapping("/open/advertisement/image/meta/{advertisementId}")
    fun getAllMetadataByAdvertisementId(
        @PathVariable("advertisementId") advertisementId: Long
    ): ResponseEntity<GetAllAdImageMetadataResponse> {
        return ResponseEntity.ok().body(
            GetAllAdImageMetadataResponse.of(
                FrontErrorCode.OK.code,
                FrontErrorCode.OK.message,
                advertisementImageService.findAllMetaDataByAdvertisementId(advertisementId)
            )
        )
    }

    @GetMapping("/open/advertisement/image/{imageUri}")
    fun getByIdentifiedUrl(
        @PathVariable imageUri: String
    ): ResponseEntity<ByteArray> {
        val result = advertisementImageService.findByIdentifiedUri(imageUri)
        return ResponseEntity.ok()
            .header(
                HttpHeaders.CONTENT_DISPOSITION,
                "inline; filename=\"test.${result.imageType}\"")
            .contentType(MediaType.parseMediaType(result.imageType))
            .body(result.imageBytes)
    }
}