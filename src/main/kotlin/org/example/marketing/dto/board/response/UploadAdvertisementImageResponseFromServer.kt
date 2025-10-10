package org.example.marketing.dto.board.response

import org.example.marketing.enums.MSAServiceErrorCode
import org.springframework.http.HttpStatus

data class UploadAdvertisementImageResponseFromServer(
    val saveAdvertisementImageResult: SaveAdvertisementImageResult?,
    val httpStatus: HttpStatus,
    val msaServiceErrorCode: MSAServiceErrorCode,
    val errorMessage: String?,
    val logics: String?,
)