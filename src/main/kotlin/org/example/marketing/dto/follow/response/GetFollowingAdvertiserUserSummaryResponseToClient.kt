package org.example.marketing.dto.follow.response

import org.example.marketing.dto.error.FrontErrorResponse
import org.example.marketing.enums.FrontErrorCode

data class GetFollowingAdvertiserUserSummaryResponseToClient(
    val result: List<FollowingAdvertiserUserSummary>,
    override val frontErrorCode: Int,
    override val errorMessage: String
) : FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            result: List<FollowingAdvertiserUserSummary>,
            frontErrorCode: FrontErrorCode
        ): GetFollowingAdvertiserUserSummaryResponseToClient {
            return GetFollowingAdvertiserUserSummaryResponseToClient(
                result = result,
                frontErrorCode = frontErrorCode.code,
                errorMessage = frontErrorCode.message
            )
        }
    }
}
