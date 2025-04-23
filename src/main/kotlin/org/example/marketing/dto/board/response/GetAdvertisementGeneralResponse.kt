package org.example.marketing.dto.board.response

import org.example.marketing.domain.board.Advertisement
import org.example.marketing.domain.board.AdvertisementGeneral
import org.example.marketing.domain.board.AdvertisementGeneralForReturn
import org.example.marketing.dto.error.FrontErrorResponse

data class GetAdvertisementGeneralResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val advertisement: AdvertisementGeneralForReturn
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            advertisement: AdvertisementGeneralForReturn
        ): GetAdvertisementGeneralResponse {
            return GetAdvertisementGeneralResponse(
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage,
                advertisement = advertisement
            )
        }
    }
}