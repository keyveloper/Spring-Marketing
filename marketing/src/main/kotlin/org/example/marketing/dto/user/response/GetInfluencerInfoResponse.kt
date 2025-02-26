package org.example.marketing.dto.user.response

import org.example.marketing.dto.error.FrontErrorResponse
import org.example.marketing.Dao.user.Influencer

data class GetInfluencerInfoResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val influencer: Influencer
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            influencer: Influencer,
            frontErrorCode: Int,
            errorMessage: String
        ): GetInfluencerInfoResponse {
            return GetInfluencerInfoResponse(
                frontErrorCode,
                errorMessage,
                influencer
            )
        }
    }
}