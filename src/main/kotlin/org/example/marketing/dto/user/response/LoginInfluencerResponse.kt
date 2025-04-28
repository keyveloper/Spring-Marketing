package org.example.marketing.dto.user.response

import org.example.marketing.dto.error.FrontErrorResponse

data class LoginInfluencerResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val result: LoginInfluencerResult
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            result: LoginInfluencerResult
        ): LoginInfluencerResponse {
            return LoginInfluencerResponse(
                frontErrorCode,
                errorMessage,
                result = result
            )
        }
    }
}


