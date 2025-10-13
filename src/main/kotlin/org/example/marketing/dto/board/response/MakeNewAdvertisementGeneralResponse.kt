package org.example.marketing.dto.board.response

import org.example.marketing.dto.error.FrontErrorResponse

data class MakeNewAdvertisementGeneralResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val result: MakeNewAdvertisementGeneralResult,
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            result: MakeNewAdvertisementGeneralResult
        ): MakeNewAdvertisementGeneralResponse {
            return MakeNewAdvertisementGeneralResponse(
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage,
                result = result
            )
        }
    }
}