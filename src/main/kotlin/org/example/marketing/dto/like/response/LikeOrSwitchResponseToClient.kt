package org.example.marketing.dto.like.response

import org.example.marketing.dto.error.FrontErrorResponse

data class LikeOrSwitchResponseToClient(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val result: LikeOrSwitchResult?
) : FrontErrorResponse(frontErrorCode, errorMessage)
