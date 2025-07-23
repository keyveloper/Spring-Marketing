package org.example.marketing.dto.noti.response

import org.example.marketing.dto.error.FrontErrorResponse

data class UploadNotiToAdvertiserResponseToClient(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val result: UploadNotiToAdvertiserResult?
) : FrontErrorResponse(frontErrorCode, errorMessage)
