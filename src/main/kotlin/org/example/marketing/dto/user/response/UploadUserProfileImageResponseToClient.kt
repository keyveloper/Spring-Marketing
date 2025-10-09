package org.example.marketing.dto.user.response

import org.example.marketing.domain.user.UserProfileImageInfo
import org.example.marketing.dto.error.FrontErrorResponse

class UploadUserProfileImageResponseToClient(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val result: UserProfileImageInfo
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object{
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            result: UserProfileImageInfo
        ): UploadUserProfileImageResponseToClient {
            return UploadUserProfileImageResponseToClient(
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage,
                result = result
            )
        }
    }
}