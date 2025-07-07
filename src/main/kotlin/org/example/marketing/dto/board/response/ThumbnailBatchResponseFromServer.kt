package org.example.marketing.dto.board.response

import org.example.marketing.enums.MSAServiceErrorCode
import org.springframework.http.HttpStatus

data class ThumbnailBatchResponseFromServer(
    val result: List<ThumbnailMetadataWithUrl>,
    val httpStatus: HttpStatus,
    val msaServiceErrorCode: MSAServiceErrorCode,
    val errorMessage: String? = null,
    val logics: String? = null
)
