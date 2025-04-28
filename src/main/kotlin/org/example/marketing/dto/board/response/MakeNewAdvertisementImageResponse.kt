package org.example.marketing.dto.board.response

import org.example.marketing.dto.error.FrontErrorResponse

data class MakeNewAdvertisementImageResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val result: MakeNewAdvertisementImageResult
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object{
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            result: MakeNewAdvertisementImageResult
        ): MakeNewAdvertisementImageResponse {
            return MakeNewAdvertisementImageResponse(
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage,
                result = result
            )
        }
    }
}