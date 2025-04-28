package org.example.marketing.controller

import org.apache.tika.metadata.HttpHeaders
import org.example.marketing.dto.board.request.MakeNewAdvertisementImageRequest
import org.example.marketing.dto.board.request.SetAdvertisementThumbnailRequest
import org.example.marketing.dto.board.response.MakeNewAdvertisementImageResponse
import org.example.marketing.dto.board.response.SetAdvertisementThumbnailResponse
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.service.AdvertisementImageService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
class AdvertisementImageController(
    private val advertisementImageService: AdvertisementImageService
) {

    @PostMapping(
        "/advertisement/image",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE]
    )
    fun save(
        @RequestPart("meta") meta: MakeNewAdvertisementImageRequest,
        @RequestPart("file") file: MultipartFile
    ): ResponseEntity<MakeNewAdvertisementImageResponse> {
        return ResponseEntity.ok().body(
            MakeNewAdvertisementImageResponse.of(
                FrontErrorCode.OK.code,
                FrontErrorCode.OK.message,
                advertisementImageService.save(meta, file)
            )
        )
    }

    @PostMapping("/advertisement/image/thumbnail")
    fun setThumbNailById(
        @RequestBody request: SetAdvertisementThumbnailRequest,
    )
    :ResponseEntity<SetAdvertisementThumbnailResponse> {
        advertisementImageService.setThumbnailImage(request)
        return ResponseEntity.ok().body(
            SetAdvertisementThumbnailResponse.of(
                FrontErrorCode.OK.code,
                FrontErrorCode.OK.message
            )
        )
    }

    @GetMapping("/advertisement/image/thumbnail/url/{advertisementId}")
    fun getThumbnailUrlByAdvertisementId(
        @PathVariable("advertisementId") advertisementId: Long
    ) {}

    @GetMapping("/advertisement/image/urls/{advertisementId}")
    fun getAllUrlsByAdvertisementId(
        @PathVariable("advertisementId") advertisementId: Long
    ) {}

    @GetMapping("{imageUrl}")
    fun getByIdentifiedUrl(
        @PathVariable imageUrl: String
    ): ResponseEntity<ByteArray> {
        val result = advertisementImageService.findByIdentifiedUrl(imageUrl)
        return ResponseEntity.ok()
            .header(
                HttpHeaders.CONTENT_DISPOSITION,
                "inline; filename=\"test.${result.imageType}\"")
            .contentType(MediaType.parseMediaType(result.imageType))
            .body(result.imageBytes)
    }
}