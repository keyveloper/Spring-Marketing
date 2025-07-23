package org.example.marketing.dto.follow.response

import org.example.marketing.dto.error.FrontErrorResponse

data class GetFollowingResponseToClient(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val result: GetFollowingResult?
) : FrontErrorResponse(frontErrorCode, errorMessage)
