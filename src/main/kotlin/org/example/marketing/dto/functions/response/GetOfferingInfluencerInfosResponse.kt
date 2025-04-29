package org.example.marketing.dto.functions.response

import org.example.marketing.dto.error.FrontErrorResponse

data class GetOfferingInfluencerInfosResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val infos: List<OfferingInfluencerInfo>
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            infos: List<OfferingInfluencerInfo>
        ): GetOfferingInfluencerInfosResponse =
            GetOfferingInfluencerInfosResponse(
                frontErrorCode,
                errorMessage,
                infos
            )
    }
}
