package org.example.marketing.dto.board.response

import org.example.marketing.domain.board.AdvertisementImageInfo
import org.example.marketing.dto.error.FrontErrorResponse

data class UploadAdvertisementImageResponseToClient(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val result: AdvertisementImageInfo
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            result: AdvertisementImageInfo
        ): UploadAdvertisementImageResponseToClient {
            return UploadAdvertisementImageResponseToClient(
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage,
                result = result
            )
        }
    }
}
