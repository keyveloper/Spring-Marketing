package org.example.marketing.dto.functions.response

import org.example.marketing.dto.error.FrontErrorResponse

data class ApplyReviewResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val createdApplicationId: Long
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            createdApplicationId: Long
        ): ApplyReviewResponse {
            return ApplyReviewResponse(
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage,
                createdApplicationId = createdApplicationId
            )
        }
    }
}
