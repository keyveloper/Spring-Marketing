package org.example.marketing.dto.user.response

import org.example.marketing.domain.user.InfluencerProfileImage
import org.example.marketing.dto.error.FrontErrorResponse

data class MakeNewInfluencerProfileImageResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val result: InfluencerProfileImage
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object{
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            result: InfluencerProfileImage
        ): MakeNewInfluencerProfileImageResponse {
            return MakeNewInfluencerProfileImageResponse(
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage,
                result = result
            )
        }
    }
}