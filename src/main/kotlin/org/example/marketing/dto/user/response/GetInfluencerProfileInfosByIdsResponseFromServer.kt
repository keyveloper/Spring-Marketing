package org.example.marketing.dto.user.response

import org.example.marketing.enums.MSAServiceErrorCode
import org.springframework.http.HttpStatus

data class GetInfluencerProfileInfosByIdsResponseFromServer(
    val getInfluencerProfileInfosByIdsResult: GetInfluencerProfileInfosByIdsResult?,
    val httpStatus: HttpStatus,
    val msaServiceErrorCode: MSAServiceErrorCode,
    val errorMessage: String? = null,
    val logics: String? = null
) {
    companion object {
        fun of(
            result: GetInfluencerProfileInfosByIdsResult?,
            httpStatus: HttpStatus,
            msaServiceErrorCode: MSAServiceErrorCode,
            errorMessage: String? = null,
            logics: String? = null
        ): GetInfluencerProfileInfosByIdsResponseFromServer {
            return GetInfluencerProfileInfosByIdsResponseFromServer(
                getInfluencerProfileInfosByIdsResult = result,
                httpStatus = httpStatus,
                msaServiceErrorCode = msaServiceErrorCode,
                errorMessage = errorMessage,
                logics = logics
            )
        }
    }
}
