package org.example.marketing.dto.timeline.response

import org.example.marketing.dto.error.FrontErrorResponse

data class UploadTimelineAdResponseToClient(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val result: UploadTimelineAdResult?
) : FrontErrorResponse(frontErrorCode, errorMessage)
