package org.example.marketing.dto.keyword

import org.example.marketing.dto.error.FrontErrorResponse

data class GetScrappedTopBlogVisitStatResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val topBloggerStat: ScrappedTopBLoggerStatFromScrapperServer,
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            topBloggerStat: ScrappedTopBLoggerStatFromScrapperServer
        ): GetScrappedTopBlogVisitStatResponse {
            return GetScrappedTopBlogVisitStatResponse(
                frontErrorCode,
                errorMessage,
                topBloggerStat
            )
        }
    }
}