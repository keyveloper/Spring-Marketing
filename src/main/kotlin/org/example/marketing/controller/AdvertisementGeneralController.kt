package org.example.marketing.controller

import jakarta.validation.Valid
import org.example.marketing.dto.board.request.*
import org.example.marketing.dto.board.response.*
import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.service.AdvertisementGeneralService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class AdvertisementGeneralController( // only for general advertisement
    private val advertisementService: AdvertisementGeneralService
) {
    @PostMapping("/test/advertisement/general")
    fun save(
        @Valid @RequestBody request: MakeNewAdvertisementGeneralRequest
    ): ResponseEntity<MakeNewAdvertisementResponse> {
        return ResponseEntity.ok().body(
            MakeNewAdvertisementResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                createdId = advertisementService.save(request)
            )
        )
    }


    @GetMapping("/test/advertisement/general/{targetId}")
    fun getById(
        @PathVariable targetId: Long
    ): ResponseEntity<GetAdvertisementGeneralResponse> {
        return ResponseEntity.ok().body(
            GetAdvertisementGeneralResponse.of(
                frontErrorCode = FrontErrorCode.OK.code,
                errorMessage = FrontErrorCode.OK.message,
                advertisement = advertisementService.findById(targetId)
            )
        )
    }
    

    @PostMapping("/test/advertisement/general/update")
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

    @DeleteMapping("/test/advertisement/general")
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