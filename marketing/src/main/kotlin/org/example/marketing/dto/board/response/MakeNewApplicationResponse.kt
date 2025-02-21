package org.example.marketing.dto.board.response

import org.example.marketing.dto.error.FrontErrorResponse

data class MakeNewApplicationResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val createdId:Long
): FrontErrorResponse(frontErrorCode, errorMessage)