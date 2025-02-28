package org.example.marketing.dto.user.response

import org.example.marketing.dto.error.FrontErrorResponse

data class MakeNewAdminResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val createdId: Long
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            createdId: Long
        ): MakeNewAdminResponse {
            return MakeNewAdminResponse(
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage,
                createdId = createdId
            )
        }
    }
}