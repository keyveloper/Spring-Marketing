package org.example.marketing.dto.user.response

import org.example.marketing.dto.error.FrontErrorResponse

data class UploadInfluencerProfileImageResponseToClient(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val result: SaveUserProfileImageResult
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            result: SaveUserProfileImageResult
        ): UploadInfluencerProfileImageResponseToClient {
            return UploadInfluencerProfileImageResponseToClient(
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage,
                result = result
            )
        }
    }
}
