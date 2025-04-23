package org.example.marketing.dto.board.response

import org.example.marketing.dto.error.FrontErrorResponse

data class MakeNewAdvertisementDeliveryResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val createdId: Long
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            createdId: Long
        ): MakeNewAdvertisementDeliveryResponse {
            return MakeNewAdvertisementDeliveryResponse(
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage,
                createdId = createdId
            )
        }
    }
}

