package org.example.marketing.controller

import jakarta.validation.Valid
import org.example.marketing.domain.user.AdvertiserPrincipal
import org.example.marketing.dto.board.request.*
import org.example.marketing.dto.board.response.*
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.service.AdvertisementImageDslService
import org.example.marketing.service.AdvertisementGeneralService
import org.example.marketing.service.AdvertisementPackageService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
class AdvertisementCRUDController( // only for general advertisement
    private val advertisementService: AdvertisementGeneralService,
    private val advertisementPackageService: AdvertisementPackageService,
    private val advertisementImageDslService: AdvertisementImageDslService
) {
    @PostMapping("/advertisement/general")
    fun save(
        @AuthenticationPrincipal advertiserPrincipal: AdvertiserPrincipal,
        @Valid @RequestBody request: MakeNewAdvertisementGeneralRequest
    ): ResponseEntity<MakeNewAdvertisementGeneralResponse> {
        return ResponseEntity.ok().body(
            MakeNewAdvertisementGeneralResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                createdId = advertisementService.save(
                    advertiserPrincipal.userId,
                    request
                )
            )
        )
    }


    @GetMapping("/open/advertisement/general/{targetId}")
    fun getById(
        @PathVariable targetId: Long
    ): ResponseEntity<GetAdvertisementGeneralResponse> {
        return ResponseEntity.ok().body(
            GetAdvertisementGeneralResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                advertisement = advertisementPackageService.findByAdvertisementId(targetId)
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

    @DeleteMapping("/advertisement")
    fun delete(
        @Valid @RequestBody request: DeleteAdvertisementRequest
    ): ResponseEntity<DeleteAdvertisementResponse> {
        advertisementImageDslService.deleteAdvertisement(request.targetId)
        return ResponseEntity.ok().body(
            DeleteAdvertisementResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
            )
        )
    }
}