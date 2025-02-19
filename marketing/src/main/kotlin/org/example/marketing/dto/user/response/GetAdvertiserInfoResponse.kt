package org.example.marketing.dto.user.response

import org.example.marketing.dto.error.FrontErrorResponse

data class GetAdvertiserInfoResponse(

    val advertiserInfo: GetAdvertiserInfoResult,

    override val frontErrorCode: Int,

    override val errorMessage: String?

): FrontErrorResponse(frontErrorCode, errorMessage)