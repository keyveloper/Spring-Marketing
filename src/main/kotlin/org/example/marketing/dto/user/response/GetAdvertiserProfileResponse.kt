package org.example.marketing.dto.user.response

import org.example.marketing.domain.user.AdvertiserProfileInfo
import org.example.marketing.dto.error.FrontErrorResponse

data class GetAdvertiserProfileResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val result: AdvertiserProfileInfo
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            result: AdvertiserProfileInfo
        ): GetAdvertiserProfileResponse {
            return GetAdvertiserProfileResponse(
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage,
                result = result
            )
        }
    }
}
