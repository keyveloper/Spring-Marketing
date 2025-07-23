package org.example.marketing.dto.follow.response

import org.example.marketing.dto.error.FrontErrorResponse

data class FollowOrSwitchResponseToClient(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val result: FollowOrSwitchResult?
) : FrontErrorResponse(frontErrorCode, errorMessage)
