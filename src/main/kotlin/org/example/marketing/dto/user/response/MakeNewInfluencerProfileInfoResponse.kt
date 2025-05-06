package org.example.marketing.dto.user.response

import org.example.marketing.dto.board.response.MakeNewAdvertisementDeliveryResponse
import org.example.marketing.dto.error.FrontErrorResponse

data class MakeNewInfluencerProfileInfoResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val createdId: Long
    ): FrontErrorResponse(frontErrorCode, errorMessage) {
        companion object {
            fun of(
                frontErrorCode: Int,
                errorMessage: String,
                createdId: Long
            ): MakeNewInfluencerProfileInfoResponse {
                return MakeNewInfluencerProfileInfoResponse(
                    frontErrorCode = frontErrorCode,
                    errorMessage = errorMessage,
                    createdId = createdId
                )
            }
        }
    }