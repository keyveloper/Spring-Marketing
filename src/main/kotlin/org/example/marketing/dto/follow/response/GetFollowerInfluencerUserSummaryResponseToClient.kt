package org.example.marketing.dto.follow.response

import org.example.marketing.dto.error.FrontErrorResponse
import org.example.marketing.enums.FrontErrorCode

data class GetFollowerInfluencerUserSummaryResponseToClient(
    val result: List<FollowerInfluencerUserSummary>,
    override val frontErrorCode: Int,
    override val errorMessage: String
) : FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            result: List<FollowerInfluencerUserSummary>,
            frontErrorCode: FrontErrorCode
        ): GetFollowerInfluencerUserSummaryResponseToClient {
            return GetFollowerInfluencerUserSummaryResponseToClient(
                result = result,
                frontErrorCode = frontErrorCode.code,
                errorMessage = frontErrorCode.message
            )
        }
    }
}
