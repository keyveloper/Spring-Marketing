package org.example.marketing.dto.user.response

import org.example.marketing.dto.error.FrontErrorResponse

data class MakeNewInfluencerResponse(
    val influencerId: Long,
    override val frontErrorCode: Int,
    override val errorMessage: String?
): FrontErrorResponse(frontErrorCode, errorMessage)