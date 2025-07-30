package org.example.marketing.dto.like.response

import org.example.marketing.dto.error.FrontErrorResponse

data class UnLikeResponseToClient(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val result: UnLikeResult?
) : FrontErrorResponse(frontErrorCode, errorMessage)
