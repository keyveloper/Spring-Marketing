package org.example.marketing.dto.user.response

import org.example.marketing.domain.user.AdvertiserProfileImage
import org.example.marketing.dto.error.FrontErrorResponse

data class MakeNewAdvertiserProfileImageResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val result: AdvertiserProfileImage
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object{
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            result: AdvertiserProfileImage
        ): MakeNewAdvertiserProfileImageResponse {
            return MakeNewAdvertiserProfileImageResponse(
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage,
                result = result
            )
        }
    }
}