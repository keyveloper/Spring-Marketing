package org.example.marketing.dto.follow.response

import org.example.marketing.enums.MSAServiceErrorCode
import org.springframework.http.HttpStatus

data class UnFollowResponseFromServer(
    val httpStatus: HttpStatus,
    val msaServiceErrorCode: MSAServiceErrorCode,
    val errorMessage: String?,
    val logics: String?,
    val result: UnFollowResultFromServer
)
