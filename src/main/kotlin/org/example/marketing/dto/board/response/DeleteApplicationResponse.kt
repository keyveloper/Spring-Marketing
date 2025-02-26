package org.example.marketing.dto.board.response

import org.example.marketing.dto.error.FrontErrorResponse

data class DeleteApplicationResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val deletedId:Long
): FrontErrorResponse(frontErrorCode, errorMessage)