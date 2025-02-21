package org.example.marketing.dto.auth

import org.example.marketing.dto.error.FrontErrorResponse

data class LogOutResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val loginId: String,

    ): FrontErrorResponse(frontErrorCode, errorMessage) {
        companion object {
            fun of(
                frontErrorCode: Int,
                errorMessage: String,
                loginId: String,
            ): LogOutResponse {
                return LogOutResponse(
                    frontErrorCode = frontErrorCode,
                    errorMessage = errorMessage,
                    loginId = loginId,
                )
            }
        }
    }