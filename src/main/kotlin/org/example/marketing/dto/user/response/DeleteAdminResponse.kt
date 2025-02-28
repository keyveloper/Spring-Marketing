package org.example.marketing.dto.user.response

import org.example.marketing.dto.error.FrontErrorResponse

data class DeleteAdminResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val effectedRowsCount: Int,
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object{
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            effectedRowsCount: Int
        ): DeleteAdminResponse {
            return DeleteAdminResponse(
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage,
                effectedRowsCount = effectedRowsCount
            )
        }
    }
}