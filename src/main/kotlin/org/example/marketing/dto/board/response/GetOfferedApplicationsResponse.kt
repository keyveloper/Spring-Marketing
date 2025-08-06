package org.example.marketing.dto.board.response

import org.example.marketing.dto.error.FrontErrorResponse

data class GetOfferedApplicationsResponse(
    val result: GetOfferedApplicationsResult,
    override val frontErrorCode: Int,
    override val errorMessage: String
) : FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            result: GetOfferedApplicationsResult,
            frontErrorCode: Int,
            errorMessage: String
        ): GetOfferedApplicationsResponse {
            return GetOfferedApplicationsResponse(
                result = result,
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage
            )
        }
    }
}
