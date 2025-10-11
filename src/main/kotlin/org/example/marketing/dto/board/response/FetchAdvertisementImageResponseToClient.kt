package org.example.marketing.dto.board.response

import org.example.marketing.dto.error.FrontErrorResponse

data class FetchAdvertisementImageResponseToClient(
    val result: List<AdvertisementImageMetadataWithUrl>,
    override val frontErrorCode: Int,
    override val errorMessage: String,
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            result: List<AdvertisementImageMetadataWithUrl>,
            frontErrorCode: Int,
            errorMessage: String
        ): FetchAdvertisementImageResponseToClient {
            return FetchAdvertisementImageResponseToClient(
                result,
                frontErrorCode,
                errorMessage
            )
        }
    }
}
