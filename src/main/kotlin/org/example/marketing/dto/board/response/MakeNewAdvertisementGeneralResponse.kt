package org.example.marketing.dto.board.response

import org.example.marketing.dto.error.FrontErrorResponse
import org.example.marketing.dto.user.response.MakeNewAdvertiserResponse

data class MakeNewAdvertisementResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val createdId: Long
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            createdId: Long
        ): MakeNewAdvertisementResponse {
            return MakeNewAdvertisementResponse(
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage,
                createdId = createdId
            )
        }
    }
}