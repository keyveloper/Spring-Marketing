package org.example.marketing.dto.functions.response

import org.example.marketing.dto.error.FrontErrorResponse

data class NewOfferReviewResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val createdEntityId: Long
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            createdEntityId: Long
        ): NewOfferReviewResponse {
            return NewOfferReviewResponse(
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage,
                createdEntityId = createdEntityId
            )
        }
    }
}
