package org.example.marketing.dto.user.response

import org.example.marketing.enums.MSAServiceErrorCode
import org.springframework.http.HttpStatus

data class GetInfluencerProfileInfoWithImagesResponseToClient(
    val result: GetInfluencerProfileInfoWithImages?,
    val httpStatus: HttpStatus,
    val msaServiceErrorCode: MSAServiceErrorCode,
    val errorMessage: String? = null,
    val logics: String? = null
) {
    companion object {
        fun of(
            result: GetInfluencerProfileInfoWithImages?,
            httpStatus: HttpStatus,
            msaServiceErrorCode: MSAServiceErrorCode,
            errorMessage: String? = null,
            logics: String? = null
        ): GetInfluencerProfileInfoWithImagesResponseToClient {
            return GetInfluencerProfileInfoWithImagesResponseToClient(
                result = result,
                httpStatus = httpStatus,
                msaServiceErrorCode = msaServiceErrorCode,
                errorMessage = errorMessage,
                logics = logics
            )
        }
    }
}
