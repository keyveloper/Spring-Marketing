package org.example.marketing.dto.board.response

import org.example.marketing.dto.error.FrontErrorResponse

data class GetMyAdvertisementsResponse(
    val result: GetMyAdvertisementsResult,
    override val frontErrorCode: Int,
    override val errorMessage: String
) : FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            result: GetMyAdvertisementsResult,
            frontErrorCode: Int,
            errorMessage: String
        ): GetMyAdvertisementsResponse {
            return GetMyAdvertisementsResponse(
                result = result,
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage
            )
        }
    }
}
