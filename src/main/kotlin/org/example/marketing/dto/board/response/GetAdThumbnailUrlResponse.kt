package org.example.marketing.dto.board.response

import org.example.marketing.dto.error.FrontErrorResponse

data class GetAdThumbnailUrlResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val url: String,
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            url: String,
        ): GetAdThumbnailUrlResponse {
            return GetAdThumbnailUrlResponse(
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage,
                url = url
            )
        }
    }
}


