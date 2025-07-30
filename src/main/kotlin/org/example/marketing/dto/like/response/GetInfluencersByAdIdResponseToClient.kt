package org.example.marketing.dto.like.response

import org.example.marketing.dto.error.FrontErrorResponse

data class GetInfluencersByAdIdResponseToClient(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val result: GetInfluencersByAdIdResult?
) : FrontErrorResponse(frontErrorCode, errorMessage)
