package org.example.marketing.dto.user.response

import org.example.marketing.dto.error.FrontErrorResponse

data class MakeNewAdvertiserResponse(
    val advertiserId: Long,
    override val frontErrorCode: Int,
    override val errorMessage: String?
): FrontErrorResponse(frontErrorCode, errorMessage)