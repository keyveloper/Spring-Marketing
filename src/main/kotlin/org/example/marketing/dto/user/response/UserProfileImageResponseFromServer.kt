package org.example.marketing.dto.user.response

import org.example.marketing.enums.MSAServiceErrorCode
import org.springframework.http.HttpStatus

data class UserProfileImageResponseFromServer(
    val result: List<UserProfileImageMetadataWithUrl>,
    val httpStatus: HttpStatus,
    val msaServiceErrorCode: MSAServiceErrorCode,
    val errorMessage: String? = null,
    val logics: String? = null
) {
    companion object {
        fun of(
            result: List<UserProfileImageMetadataWithUrl>,
            httpStatus: HttpStatus,
            msaServiceErrorCode: MSAServiceErrorCode,
            errorMessage: String? = null,
            logics: String? = null
        ): UserProfileImageResponseFromServer {
            return UserProfileImageResponseFromServer(
                result = result,
                httpStatus = httpStatus,
                msaServiceErrorCode = msaServiceErrorCode,
                errorMessage = errorMessage,
                logics = logics
            )
        }
    }
}
