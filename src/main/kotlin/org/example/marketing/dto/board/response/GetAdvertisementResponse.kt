package org.example.marketing.dto.board.response

import org.example.marketing.domain.AdvertisementGeneral
import org.example.marketing.dto.error.FrontErrorResponse

data class GetAdvertisementResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val advertisement: AdvertisementGeneral?
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            advertisement: AdvertisementGeneral?
        ): GetAdvertisementResponse {
            return GetAdvertisementResponse(
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage,
                advertisement = advertisement
            )
        }
    }
}