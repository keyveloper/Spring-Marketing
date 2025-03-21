package org.example.marketing.dto.board.response

import org.example.marketing.dto.error.FrontErrorResponse
import org.example.marketing.dto.user.response.MakeNewAdvertiserResponse

data class DeleteAdvertisementResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val deletedId: Long,
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            deletedId: Long,
        ): DeleteAdvertisementResponse {
            return DeleteAdvertisementResponse(
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage,
                deletedId = deletedId,
            )
        }
    }
}