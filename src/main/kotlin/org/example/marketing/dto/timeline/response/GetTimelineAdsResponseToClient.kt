package org.example.marketing.dto.timeline.response

import org.example.marketing.dto.error.FrontErrorResponse

data class GetTimelineAdsResponseToClient(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val result: GetTimelineAdsResult?
) : FrontErrorResponse(frontErrorCode, errorMessage)
