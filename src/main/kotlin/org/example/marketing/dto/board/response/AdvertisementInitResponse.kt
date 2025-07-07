package org.example.marketing.dto.board.response

import org.example.marketing.dto.error.FrontErrorResponse

data class AdvertisementInitResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val result: AdvertisementInitResult
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            result: AdvertisementInitResult
        ): AdvertisementInitResponse {
            return AdvertisementInitResponse(
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage,
                result = result
            )
        }
    }
}
