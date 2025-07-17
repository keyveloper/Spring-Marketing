package org.example.marketing.dto.user.response

import org.example.marketing.dto.error.FrontErrorResponse

data class UploadInfluencerProfileInfoResponseToClient(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val result: UploadInfluencerProfileInfoResult
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            result: UploadInfluencerProfileInfoResult
        ): UploadInfluencerProfileInfoResponseToClient {
            return UploadInfluencerProfileInfoResponseToClient(
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage,
                result = result
            )
        }
    }
}


