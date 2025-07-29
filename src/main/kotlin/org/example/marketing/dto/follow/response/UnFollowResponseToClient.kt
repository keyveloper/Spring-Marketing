package org.example.marketing.dto.follow.response

import org.example.marketing.dto.error.FrontErrorResponse

data class UnFollowResponseToClient(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val result: UnFollowResult?
) : FrontErrorResponse(frontErrorCode, errorMessage)
