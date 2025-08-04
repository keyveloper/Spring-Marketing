package org.example.marketing.dto.user.response

import org.example.marketing.enums.MSAServiceErrorCode
import org.springframework.http.HttpStatus

data class GetAdvertiserProfileInfosByIdsResponseFromServer(
    val getAdvertiserProfileInfosByIdsResult: GetAdvertiserProfileInfosByIdsResult?,
    val httpStatus: HttpStatus,
    val msaServiceErrorCode: MSAServiceErrorCode,
    val errorMessage: String? = null,
    val logics: String? = null
) {
    companion object {
        fun of(
            result: GetAdvertiserProfileInfosByIdsResult?,
            httpStatus: HttpStatus,
            msaServiceErrorCode: MSAServiceErrorCode,
            errorMessage: String? = null,
            logics: String? = null
        ): GetAdvertiserProfileInfosByIdsResponseFromServer {
            return GetAdvertiserProfileInfosByIdsResponseFromServer(
                getAdvertiserProfileInfosByIdsResult = result,
                httpStatus = httpStatus,
                msaServiceErrorCode = msaServiceErrorCode,
                errorMessage = errorMessage,
                logics = logics
            )
        }
    }
}
