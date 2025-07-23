package org.example.marketing.dto.noti.response

import org.example.marketing.dto.error.FrontErrorResponse

data class GetNotiToAdvertisersByAdvertiserIdResponseToClient(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val result: List<NotiToAdvertiserInfo>?
) : FrontErrorResponse(frontErrorCode, errorMessage)
