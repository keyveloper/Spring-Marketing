package org.example.marketing.dto.user.response

import org.example.marketing.dto.error.FrontErrorResponse

data class ChangeUserProfileImageStatusResponseToClient(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val result: ChangeUserProfileImageStatusResult
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            result: ChangeUserProfileImageStatusResult
        ): ChangeUserProfileImageStatusResponseToClient {
            return ChangeUserProfileImageStatusResponseToClient(
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage,
                result = result
            )
        }
    }
}

