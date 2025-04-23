package org.example.marketing.dto.board.response

import org.example.marketing.domain.board.AdvertisementDelivery
import org.example.marketing.dto.error.FrontErrorResponse

data class GetDeliveryAdvertisementsTimelineByCategoryResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val advertisements: List<AdvertisementDelivery>
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            advertisements: List<AdvertisementDelivery>
        ): GetDeliveryAdvertisementsTimelineByCategoryResponse {
            return GetDeliveryAdvertisementsTimelineByCategoryResponse(
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage,
                advertisements = advertisements
            )
        }
    }
}
