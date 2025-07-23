package org.example.marketing.dto.noti.response

import org.example.marketing.dto.error.FrontErrorResponse

data class UploadNotiToInfluencerResponseToClient(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val result: UploadNotiToInfluencerResult?
) : FrontErrorResponse(frontErrorCode, errorMessage)
