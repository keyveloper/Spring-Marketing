package org.example.marketing.dto.user.response

import org.example.marketing.dto.error.FrontErrorResponse

data class MakeNewAdvertiserProfileInfoResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val createdId: Long
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            createdId: Long
        ): MakeNewAdvertiserProfileInfoResponse {
            return MakeNewAdvertiserProfileInfoResponse(
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage,
                createdId = createdId
            )
        }
    }
}
