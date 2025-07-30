package org.example.marketing.dto.like.response

import org.example.marketing.enums.MSAServiceErrorCode
import org.springframework.http.HttpStatus

data class GetLikedAdsByInfluencerIdResponseFromServer(
    val result: GetLikedAdsByInfluencerIdResult?,
    val httpStatus: HttpStatus,
    val msaServiceErrorCode: MSAServiceErrorCode,
    val errorMessage: String?,
    val logics: String?
)
