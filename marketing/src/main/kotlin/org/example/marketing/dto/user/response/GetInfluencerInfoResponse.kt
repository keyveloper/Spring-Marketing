package org.example.marketing.dto.user.response

import org.example.marketing.dto.error.FrontErrorResponse

data class GetInfluencerInfoResponse(

    val influencerInfo: GetInfluencerInfoResult,
    override val frontErrorCode: Int,
    override val errorMessage: String?

): FrontErrorResponse(frontErrorCode, errorMessage)