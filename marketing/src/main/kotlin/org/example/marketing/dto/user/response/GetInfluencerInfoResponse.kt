package org.example.marketing.dto.user.response

import org.example.marketing.dto.error.FrontErrorResponse
import org.example.marketing.entity.user.Influencer

data class GetInfluencerInfoResponse(

    val influencer: Influencer,
    override val frontErrorCode: Int,
    override val errorMessage: String?

): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            influencer: Influencer,
            frontErrorCode: Int,
            errorMessage: String?
        ): GetInfluencerInfoResponse {
            return GetInfluencerInfoResponse(
                influencer,
                frontErrorCode,
                errorMessage
            )
        }
    }
}