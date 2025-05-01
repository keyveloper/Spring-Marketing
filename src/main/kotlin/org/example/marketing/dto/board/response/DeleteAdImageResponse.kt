package org.example.marketing.dto.board.response

import org.example.marketing.dto.error.FrontErrorResponse

data class DeleteAdImageResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
): FrontErrorResponse(frontErrorCode, errorMessage)
