package org.example.marketing.dto.keyword

import org.example.marketing.dto.error.FrontErrorResponse

data class GetScrappedKeywordDetailStatResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val topBloggerStat: ScrappedTopBLoggerStatFromScrapperServer,
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            topBloggerStat: ScrappedTopBLoggerStatFromScrapperServer
        ): GetScrappedKeywordDetailStatResponse {
            return GetScrappedKeywordDetailStatResponse(
                frontErrorCode,
                errorMessage,
                topBloggerStat
            )
        }
    }
}