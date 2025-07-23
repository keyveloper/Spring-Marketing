package org.example.marketing.dto.timeline.response

import org.example.marketing.dto.error.FrontErrorResponse

data class DeleteTimelineAdResponseToClient(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val deletedRow: Int
) : FrontErrorResponse(frontErrorCode, errorMessage)
