package org.example.marketing.dto.user.response

import org.example.marketing.dto.error.FrontErrorResponse

data class MakeNewAdvertiserResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val createdId: Long,
): FrontErrorResponse(frontErrorCode, errorMessage)