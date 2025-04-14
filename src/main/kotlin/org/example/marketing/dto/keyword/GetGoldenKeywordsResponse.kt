package org.example.marketing.dto.keyword

import org.example.marketing.dto.error.FrontErrorResponse

data class GetGoldenKeywordsResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val goldenKeywords: List<GoldenKeywordStat>,
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            goldenKeywords: List<GoldenKeywordStat>
        ): GetGoldenKeywordsResponse {
            return GetGoldenKeywordsResponse(
                frontErrorCode,
                errorMessage,
                goldenKeywords
            )
        }
    }
}