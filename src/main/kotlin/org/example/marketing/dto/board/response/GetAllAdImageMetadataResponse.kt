package org.example.marketing.dto.board.response

import org.example.marketing.domain.board.AdvertisementImage
import org.example.marketing.dto.error.FrontErrorResponse

data class GetAllAdImageMetadataResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val result: List<AdvertisementImage>
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            result: List<AdvertisementImage>
        ): GetAllAdImageMetadataResponse =
            GetAllAdImageMetadataResponse(
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage,
                result = result
            )
    }
}