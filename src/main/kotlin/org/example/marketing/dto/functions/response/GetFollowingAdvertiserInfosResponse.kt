package org.example.marketing.dto.functions.response

import org.example.marketing.dto.error.FrontErrorResponse

data class GetFollowingAdvertiserInfosResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val infos: List<FollowingAdvertiserInfo>
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            infos: List<FollowingAdvertiserInfo>
        ): GetFollowingAdvertiserInfosResponse {
            return GetFollowingAdvertiserInfosResponse(
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage,
                infos = infos
            )
        }
    }
}
