package org.example.marketing.dto.noti.response

import org.example.marketing.enums.MSAServiceErrorCode
import org.springframework.http.HttpStatus

data class GetNotiToInfluencersByInfluencerIdResponseFromServer(
    val httpStatus: HttpStatus,
    val msaServiceErrorCode: MSAServiceErrorCode,
    val errorMessage: String?,
    val logics: String?,
    val result: List<NotiToInfluencerInfo>?
)
