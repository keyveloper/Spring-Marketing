package org.example.marketing.dto.functions.response

import org.example.marketing.dto.error.FrontErrorResponse

data class FollowAdvertiserResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val result: FollowAdvertiserResult
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            result: FollowAdvertiserResult
        ): FollowAdvertiserResponse {
            return FollowAdvertiserResponse(
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage,
                result = result
            )
        }
    }
}

