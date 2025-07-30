package org.example.marketing.dto.user.response

import org.example.marketing.enums.MSAServiceErrorCode
import org.springframework.http.HttpStatus

data class DeleteAdvertiserProfileInfoResponseFromServer(
    val deleteAdvertiserProfileInfoResult: DeleteAdvertiserProfileInfoResult?,
    val httpStatus: HttpStatus,
    val msaServiceErrorCode: MSAServiceErrorCode,
    val errorMessage: String? = null,
    val logics: String? = null
) {
    companion object {
        fun of(
            result: DeleteAdvertiserProfileInfoResult?,
            httpStatus: HttpStatus,
            msaServiceErrorCode: MSAServiceErrorCode,
            errorMessage: String? = null,
            logics: String? = null
        ): DeleteAdvertiserProfileInfoResponseFromServer {
            return DeleteAdvertiserProfileInfoResponseFromServer(
                deleteAdvertiserProfileInfoResult = result,
                httpStatus = httpStatus,
                msaServiceErrorCode = msaServiceErrorCode,
                errorMessage = errorMessage,
                logics = logics
            )
        }
    }
}
