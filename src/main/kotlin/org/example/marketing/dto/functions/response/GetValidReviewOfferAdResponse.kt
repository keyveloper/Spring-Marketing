package org.example.marketing.dto.functions.response

import org.example.marketing.domain.functions.InfluencerValidReviewOfferAd
import org.example.marketing.dto.error.FrontErrorResponse

data class GetValidReviewOfferAdResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val result: List<InfluencerValidReviewOfferAd>
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            result: List<InfluencerValidReviewOfferAd>
        ): GetValidReviewOfferAdResponse =
            GetValidReviewOfferAdResponse(
                frontErrorCode,
                errorMessage,
                result
            )
    }
}

