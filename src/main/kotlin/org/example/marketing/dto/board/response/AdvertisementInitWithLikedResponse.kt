package org.example.marketing.dto.board.response

import org.example.marketing.dto.error.FrontErrorResponse

data class AdvertisementInitWithLikedResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val result: AdvertisementInitWithLikedResult
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            result: AdvertisementInitWithLikedResult
        ): AdvertisementInitWithLikedResponse {
            return AdvertisementInitWithLikedResponse(
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage,
                result = result
            )
        }
    }
}
