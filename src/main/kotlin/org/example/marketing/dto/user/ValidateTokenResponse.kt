package org.example.marketing.dto.user

import org.example.marketing.dto.error.FrontErrorResponse
import org.example.marketing.enums.UserType

data class ValidateTokenResponse(
    val userId: Long,
    val userType: UserType,
    override val frontErrorCode: Int,
    override val errorMessage: String
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            userId: Long,
            userType: UserType,
            frontErrorCode: Int,
            errorMessage: String
        ): ValidateTokenResponse {
            return ValidateTokenResponse(
                userId = userId,
                userType = userType,
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage
            )
        }
    }
}