package org.example.marketing.dto.board.response

import org.example.marketing.domain.board.Advertisement
import org.example.marketing.dto.error.FrontErrorResponse

data class GetAdvertisementFreshResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val advertisements: List<Advertisement>
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            advertisements: List<Advertisement>
        ): GetAdvertisementFreshResponse {
            return GetAdvertisementFreshResponse(
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage,
                advertisements = advertisements
            )
        }
    }
}