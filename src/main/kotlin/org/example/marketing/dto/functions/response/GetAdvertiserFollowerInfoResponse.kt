package org.example.marketing.dto.functions.response

import org.example.marketing.domain.functions.AdvertiserFollowerInfo
import org.example.marketing.dto.error.FrontErrorResponse

data class GetAdvertiserFollowerInfoResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val info: AdvertiserFollowerInfo
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            info: AdvertiserFollowerInfo
        ): GetAdvertiserFollowerInfoResponse =
            GetAdvertiserFollowerInfoResponse(
                frontErrorCode,
                errorMessage,
                info
            )
    }
}
