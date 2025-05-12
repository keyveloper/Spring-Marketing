package org.example.marketing.dto.keyword

import org.example.marketing.dto.error.FrontErrorResponse

data class GenerateSeoOptimizedTitlesResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val titles: List<String>
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            titles: List<String>
        ): GenerateSeoOptimizedTitlesResponse {
            return GenerateSeoOptimizedTitlesResponse(
                frontErrorCode = frontErrorCode,
                errorMessage = errorMessage,
                titles = titles
            )
        }
    }
}
