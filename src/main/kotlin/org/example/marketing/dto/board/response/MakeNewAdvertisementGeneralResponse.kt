package org.example.marketing.dto.board.response

import org.example.marketing.dto.error.FrontErrorResponse

data class MakeNewAdvertisementGeneralResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val createdId: Long
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            createdId: Long
        ): MakeNewAdvertisementGeneralResponse {
            return MakeNewAdvertisementGeneralResponse(
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage,
                createdId = createdId
            )
        }
    }
}