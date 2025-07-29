package org.example.marketing.dto.follow.response

import org.example.marketing.dto.error.FrontErrorResponse

data class FollowResponseToClient(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val result: FollowResult?
) : FrontErrorResponse(frontErrorCode, errorMessage)
