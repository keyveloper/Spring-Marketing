package org.example.marketing.dto.board.response

import org.example.marketing.domain.Advertisement
import org.example.marketing.dto.error.FrontErrorResponse

data class GetAdvertisementResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val advertisement: Advertisement?
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            advertisement: Advertisement?
        ): GetAdvertisementResponse {
            return GetAdvertisementResponse(
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage,
                advertisement = advertisement
            )
        }
    }
}