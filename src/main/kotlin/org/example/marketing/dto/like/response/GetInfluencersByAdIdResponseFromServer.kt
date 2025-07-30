package org.example.marketing.dto.like.response

import org.example.marketing.enums.MSAServiceErrorCode
import org.springframework.http.HttpStatus

data class GetInfluencersByAdIdResponseFromServer(
    val result: GetInfluencersByAdIdResult?,
    val httpStatus: HttpStatus,
    val msaServiceErrorCode: MSAServiceErrorCode,
    val errorMessage: String?,
    val logics: String?
)
