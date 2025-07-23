package org.example.marketing.dto.follow.response

import org.example.marketing.dto.error.FrontErrorResponse

data class GetFollowersResponseToClient(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val result: GetFollowersResult?
) : FrontErrorResponse(frontErrorCode, errorMessage)
