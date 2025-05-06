package org.example.marketing.dto.user.response

import org.example.marketing.dao.user.InfluencerJoinedProfileInfo
import org.example.marketing.dto.error.FrontErrorResponse

data class GetInfluencerProfileResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val result: InfluencerJoinedProfileInfo
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            result: InfluencerJoinedProfileInfo
        ): GetInfluencerProfileResponse {
            return GetInfluencerProfileResponse(
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage,
                result = result
            )
        }
    }
}
