package org.example.marketing.dto.functions.response

import org.example.marketing.domain.functions.AdParticipatedByInfluencer
import org.example.marketing.dto.error.FrontErrorResponse

data class GetAdsParticipatedByInfluencerResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val result: List<AdParticipatedByInfluencer>
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            result: List<AdParticipatedByInfluencer>
        ): GetAdsParticipatedByInfluencerResponse =
            GetAdsParticipatedByInfluencerResponse(
                frontErrorCode,
                errorMessage,
                result
            )
    }
}


