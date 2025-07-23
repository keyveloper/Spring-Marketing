package org.example.marketing.dto.timeline.response

import org.example.marketing.enums.MSAServiceErrorCode
import org.springframework.http.HttpStatus

data class GetTimelineAdsResponseFromServer(
    val httpStatus: HttpStatus,
    val msaServiceErrorCode: MSAServiceErrorCode,
    val errorMessage: String?,
    val logics: String?,
    val result: GetTimelineAdsResult?
)
