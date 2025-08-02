package org.example.marketing.dto.user.response

import org.example.marketing.enums.MSAServiceErrorCode
import org.springframework.http.HttpStatus

data class GetUserInfoResponseFromServer(
    val httpStatus: HttpStatus,
    val msaServiceErrorCode: MSAServiceErrorCode,
    val errorMessage: String? = null,
    val logics: String? = null,
    val result: GetUserInfoResult? = null
) {
    companion object {
        fun of(
            result: GetUserInfoResult,
            httpStatus: HttpStatus = HttpStatus.OK,
            msaServiceErrorCode: MSAServiceErrorCode = MSAServiceErrorCode.OK
        ): GetUserInfoResponseFromServer {
            return GetUserInfoResponseFromServer(
                httpStatus = httpStatus,
                msaServiceErrorCode = msaServiceErrorCode,
                result = result
            )
        }
    }
}
