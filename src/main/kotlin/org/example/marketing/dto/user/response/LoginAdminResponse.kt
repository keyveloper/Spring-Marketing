package org.example.marketing.dto.user.response

import org.example.marketing.dto.error.FrontErrorResponse

data class LoginAdminResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val jwtToken: String?
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            jwtToken: String?
        ): LoginAdminResponse {
            return LoginAdminResponse(
                frontErrorCode,
                errorMessage,
                jwtToken
            )
        }
    }
}