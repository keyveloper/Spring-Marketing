package org.example.marketing.dto.board.response

import org.example.marketing.dto.error.FrontErrorResponse

data class SetAdvertisementThumbnailResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String
        ): SetAdvertisementThumbnailResponse {
            return SetAdvertisementThumbnailResponse(
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage
            )
        }
    }
}
