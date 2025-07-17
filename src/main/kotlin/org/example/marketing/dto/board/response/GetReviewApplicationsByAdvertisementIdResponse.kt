package org.example.marketing.dto.board.response

import org.example.marketing.domain.board.ReviewApplication
import org.example.marketing.dto.error.FrontErrorResponse

data class GetReviewApplicationsByAdvertisementIdResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val applications: List<ReviewApplication>
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            applications: List<ReviewApplication>
        ): GetReviewApplicationsByAdvertisementIdResponse {
            return GetReviewApplicationsByAdvertisementIdResponse(
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage,
                applications = applications
            )
        }
    }
}