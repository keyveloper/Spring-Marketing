package org.example.marketing.dto.user.response

import org.example.marketing.enums.MSAServiceErrorCode
import org.springframework.http.HttpStatus

data class GetAdvertiserProfileInfoWithImagesResponseToClient(
    val result: GetAdvertiserProfileInfoWithImages?,
    val httpStatus: HttpStatus,
    val msaServiceErrorCode: MSAServiceErrorCode,
    val errorMessage: String? = null,
    val logics: String? = null
) {
    companion object {
        fun of(
            result: GetAdvertiserProfileInfoWithImages?,
            httpStatus: HttpStatus,
            msaServiceErrorCode: MSAServiceErrorCode,
            errorMessage: String? = null,
            logics: String? = null
        ): GetAdvertiserProfileInfoWithImagesResponseToClient {
            return GetAdvertiserProfileInfoWithImagesResponseToClient(
                result = result,
                httpStatus = httpStatus,
                msaServiceErrorCode = msaServiceErrorCode,
                errorMessage = errorMessage,
                logics = logics
            )
        }
    }
}
