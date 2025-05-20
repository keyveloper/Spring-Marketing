package org.example.marketing.dto.functions.response

import org.example.marketing.domain.board.AdvertisementPackage
import org.example.marketing.dto.error.FrontErrorResponse

data class GetOwnedExpiredAdvertisementsResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val packages: List<AdvertisementPackage>
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            packages: List<AdvertisementPackage>
        ): GetOwnedExpiredAdvertisementsResponse = GetOwnedExpiredAdvertisementsResponse(
            frontErrorCode,
            errorMessage,
            packages
        )
    }
}

