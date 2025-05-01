package org.example.marketing.dto.board.response

import org.example.marketing.dto.error.FrontErrorResponse
import org.example.marketing.enums.FrontErrorCode

data class SetImageAdvertisementIdResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
        ): SetImageAdvertisementIdResponse = SetImageAdvertisementIdResponse(
            frontErrorCode = frontErrorCode,
            errorMessage = errorMessage,
        )
    }
}