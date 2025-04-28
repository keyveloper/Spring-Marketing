package org.example.marketing.dto.functions.response

import org.example.marketing.dto.error.FrontErrorResponse
import org.example.marketing.dto.functions.request.FollowRequest
import org.example.marketing.enums.FollowStatus

data class FollowResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val result: FollowResult
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            result: FollowResult
        ): FollowResponse {
            return FollowResponse(
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage,
                result = result
            )
        }
    }
}

