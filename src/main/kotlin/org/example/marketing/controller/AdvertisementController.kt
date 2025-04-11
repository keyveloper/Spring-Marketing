package org.example.marketing.controller

import jakarta.validation.Valid
import org.example.marketing.dto.board.request.*
import org.example.marketing.dto.board.response.*
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.service.AdvertisementService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class AdvertisementController(
    private val advertisementService: AdvertisementService
) {
    @PostMapping("/test/advertisement")
    fun save(
        @Valid @RequestBody request: MakeNewAdvertisementRequest
    ): ResponseEntity<MakeNewAdvertisementResponse> {
        return ResponseEntity.ok().body(
            MakeNewAdvertisementResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                createdId = advertisementService.save(request)
            )
        )
    }

    @GetMapping("/test/advertisement/{targetId}")
    fun getById(
        @PathVariable targetId: Long
    ): ResponseEntity<GetAdvertisementResponse> {
        return ResponseEntity.ok().body(
            GetAdvertisementResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                advertisement = advertisementService.findById(targetId)
            )
        )
    }

    @GetMapping("/test/advertisement/fresh")
    fun getFreshAll(): ResponseEntity<GetAdvertisementFreshResponse> {
        return ResponseEntity.ok().body(
            GetAdvertisementFreshResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                advertisements = advertisementService.findFreshAll()
            )
        )
    }



    @GetMapping("/test/advertisement/by/reviewTypes")
    fun getAllByReviewTypes(
        @Valid @RequestBody request: GetAdvertisementRequestByReviewTypes
    ): ResponseEntity<List<GetAdvertisementResponse>> {
        return ResponseEntity.ok().body(
            advertisementService.findAllByReviewTypes(request).map {
                GetAdvertisementResponse.of(
                    frontErrorCode = FrontErrorCode.OK.code,
                    errorMessage = FrontErrorCode.OK.message,
                    advertisement = it
                )
            }
        )
    }

    @GetMapping("/test/advertisement/by/channels")
    fun getAllByChannels(
        @Valid @RequestBody request: GetAdvertisementRequestByChannels
    ): ResponseEntity<List<GetAdvertisementResponse>> {
        return ResponseEntity.ok().body(
            advertisementService.findAllByChannels(request).map {
                GetAdvertisementResponse.of(
                    frontErrorCode = FrontErrorCode.OK.code,
                    errorMessage = FrontErrorCode.OK.message,
                    advertisement = it
                )
            }
        )
    }
    

    @PostMapping("/test/advertisement/update")
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

    @DeleteMapping("/test/advertisement")
    fun delete(
        @Valid @RequestBody request: DeleteAdvertisementRequest
    ): ResponseEntity<DeleteAdvertisementResponse> {
        return ResponseEntity.ok().body(
            DeleteAdvertisementResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                deletedId = advertisementService.deleteById(request)
            )
        )
    }
}