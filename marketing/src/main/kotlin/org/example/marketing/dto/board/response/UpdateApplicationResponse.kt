package org.example.marketing.dto.board.response

import org.example.marketing.dto.error.FrontErrorResponse

data class UpdateApplicationResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val updatedId:Long
): FrontErrorResponse(frontErrorCode, errorMessage)