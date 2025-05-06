package org.example.marketing.dto.board.response

import org.example.marketing.domain.board.AdvertisementPackage
import org.example.marketing.dto.error.FrontErrorResponse

data class GetAdvertisementFreshResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val packages: List<AdvertisementPackage>
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            advertisements: List<AdvertisementPackage>
        ): GetAdvertisementFreshResponse {
            return GetAdvertisementFreshResponse(
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage,
                packages = advertisements
            )
        }
    }
}