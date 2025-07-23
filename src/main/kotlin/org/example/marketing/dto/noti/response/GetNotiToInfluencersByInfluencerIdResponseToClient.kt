package org.example.marketing.dto.noti.response

import org.example.marketing.dto.error.FrontErrorResponse

data class GetNotiToInfluencersByInfluencerIdResponseToClient(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val result: List<NotiToInfluencerInfo>?
) : FrontErrorResponse(frontErrorCode, errorMessage)
