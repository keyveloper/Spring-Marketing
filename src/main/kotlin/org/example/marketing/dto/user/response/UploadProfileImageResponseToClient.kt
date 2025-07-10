package org.example.marketing.dto.user.response

import org.example.marketing.dto.error.FrontErrorResponse

data class UploadProfileImageResponseToClient(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val result: SaveAdvertisementProfileImageResult
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            result: SaveAdvertisementProfileImageResult
        ): UploadProfileImageResponseToClient {
            return UploadProfileImageResponseToClient(
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage,
                result = result
            )
        }
    }
}
