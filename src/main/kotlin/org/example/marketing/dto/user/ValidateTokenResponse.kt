package org.example.marketing.dto.user

import org.example.marketing.dto.error.FrontErrorResponse
import org.example.marketing.enums.UserType

data class ValidateTokenResponse(
    val validateResult: ValidateTokenResult,
    override val frontErrorCode: Int,
    override val errorMessage: String
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            validateResult: ValidateTokenResult,
            frontErrorCode: Int,
            errorMessage: String
        ): ValidateTokenResponse {
            return ValidateTokenResponse(
                validateResult = validateResult,
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage
            )
        }
    }
}