package org.example.marketing.dto.user.response

import org.example.marketing.dto.error.FrontErrorResponse
import org.example.marketing.enums.FrontErrorCode

data class AdvertiserProfileResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val result: AdvertiserProfileResult
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            result: AdvertiserProfileResult
        ): AdvertiserProfileResponse {
            return AdvertiserProfileResponse(
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage,
                result = result
            )
        }
    }
}