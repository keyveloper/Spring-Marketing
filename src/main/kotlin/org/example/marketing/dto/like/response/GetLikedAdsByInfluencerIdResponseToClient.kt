package org.example.marketing.dto.like.response

import org.example.marketing.dto.error.FrontErrorResponse

data class GetLikedAdsByInfluencerIdResponseToClient(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val result: GetLikedAdsByInfluencerIdResult?
) : FrontErrorResponse(frontErrorCode, errorMessage)
