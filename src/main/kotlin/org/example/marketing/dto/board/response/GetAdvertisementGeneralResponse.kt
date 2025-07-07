package org.example.marketing.dto.board.response

import org.example.marketing.domain.board.AdvertisementPackage
import org.example.marketing.dto.error.FrontErrorResponse

data class GetAdvertisementGeneralResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val advertisement:AdvertisementPackage?
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            advertisement: AdvertisementPackage?
        ): GetAdvertisementGeneralResponse {
            return GetAdvertisementGeneralResponse(
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage,
                advertisement = advertisement
            )
        }
    }
}