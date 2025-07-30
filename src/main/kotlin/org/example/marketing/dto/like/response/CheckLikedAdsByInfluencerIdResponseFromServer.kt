package org.example.marketing.dto.like.response

import org.example.marketing.enums.MSAServiceErrorCode
import org.springframework.http.HttpStatus

data class CheckLikedAdsByInfluencerIdResponseFromServer(
    val result: CheckLikedAdsByInfluencerIdResult?,
    val httpStatus: HttpStatus,
    val msaServiceErrorCode: MSAServiceErrorCode,
    val errorMessage: String?,
    val logics: String?
)
