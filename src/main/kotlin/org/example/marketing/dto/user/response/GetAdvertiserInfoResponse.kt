package org.example.marketing.dto.user.response

import org.example.marketing.dto.error.FrontErrorResponse
import org.example.marketing.dao.user.AdvertiserEntity
import org.example.marketing.domain.user.Advertiser

data class GetAdvertiserInfoResponse(
    override val frontErrorCode: Int,

    override val errorMessage: String,

    val advertiser: Advertiser,

    ): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            advertiser: Advertiser
        ): GetAdvertiserInfoResponse {
            return GetAdvertiserInfoResponse(
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage,
                advertiser = advertiser
            )
        }
    }
}