package org.example.marketing.dto.user.response

import org.example.marketing.dto.error.FrontErrorResponse
import org.example.marketing.enums.FrontErrorCode

data class GetAdvertiserProfileSummarizedResponseToClient(
    val result: GetAdvertiserProfileSummarizedResult?,
    override val frontErrorCode: Int,
    override val errorMessage: String
) : FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            result: GetAdvertiserProfileSummarizedResult?,
            frontErrorCode: FrontErrorCode
        ): GetAdvertiserProfileSummarizedResponseToClient {
            return GetAdvertiserProfileSummarizedResponseToClient(
                result = result,
                frontErrorCode = frontErrorCode.code,
                errorMessage = frontErrorCode.message
            )
        }
    }
}
