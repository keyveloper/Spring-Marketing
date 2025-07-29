package org.example.marketing.dto.board.response

import org.example.marketing.dto.error.FrontErrorResponse

data class GetMyApplicationResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val result: GetMyApplicationResult?
) : FrontErrorResponse(frontErrorCode, errorMessage)
