package org.example.marketing.dto.board.response

import org.example.marketing.domain.board.AdvertisementDelivery
import org.example.marketing.dto.error.FrontErrorResponse

data class GetAdvertisementDeliveryResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val advertisement: AdvertisementDelivery
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            advertisement: AdvertisementDelivery
        ): GetAdvertisementDeliveryResponse {
            return GetAdvertisementDeliveryResponse(
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage,
                advertisement = advertisement
            )
        }
    }
}