package org.example.marketing.dto.board.response

import org.example.marketing.dto.error.FrontErrorResponse
import org.example.marketing.dto.user.response.MakeNewAdvertiserResponse

data class UpdateAdvertisementResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val updateId: Long,
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            updateId: Long
        ): UpdateAdvertisementResponse {
            return UpdateAdvertisementResponse(
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage,
                updateId = updateId
            )
        }
    }
}