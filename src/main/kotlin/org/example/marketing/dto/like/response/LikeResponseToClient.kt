package org.example.marketing.dto.like.response

import org.example.marketing.dto.error.FrontErrorResponse

data class LikeResponseToClient(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val result: LikeResult?
) : FrontErrorResponse(frontErrorCode, errorMessage)
