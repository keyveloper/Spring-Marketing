package org.example.marketing.dto.user.response

import org.example.marketing.dto.error.FrontErrorResponse

data class LoginAdvertiserResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val jwt: String,
    val advertiserId: Long
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            result: LoginAdvertiserResult
        ): LoginAdvertiserResponse {
            return LoginAdvertiserResponse(
                frontErrorCode,
                errorMessage,
                jwt = result.jwt,
                advertiserId = result.advertiserId
            )
        }
    }
}