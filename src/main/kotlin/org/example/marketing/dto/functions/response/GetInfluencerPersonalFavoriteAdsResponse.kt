package org.example.marketing.dto.functions.response

import org.example.marketing.domain.functions.InfluencerFavoriteAdWithThumbnail
import org.example.marketing.dto.error.FrontErrorResponse

data class GetInfluencerPersonalFavoriteAdsResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val result: List<InfluencerFavoriteAdWithThumbnail>
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            result: List<InfluencerFavoriteAdWithThumbnail>
        ): GetInfluencerPersonalFavoriteAdsResponse =
            GetInfluencerPersonalFavoriteAdsResponse(
                frontErrorCode,
                errorMessage,
                result
            )
    }
}