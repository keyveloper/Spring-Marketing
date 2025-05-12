package org.example.marketing.dto.keyword

import org.example.marketing.dto.error.FrontErrorResponse

data class GetScrappedTopBlogVistStatResponse(
    override val frontErrorCode: Int,
    override val errorMessage: String,
    val topBloggerStat: ScrappedTopBLoggerStatFromScrapperServer,
): FrontErrorResponse(frontErrorCode, errorMessage) {
    companion object {
        fun of(
            frontErrorCode: Int,
            errorMessage: String,
            topBloggerStat: ScrappedTopBLoggerStatFromScrapperServer
        ): GetScrappedTopBlogVistStatResponse {
            return GetScrappedTopBlogVistStatResponse(
                frontErrorCode,
                errorMessage,
                topBloggerStat
            )
        }
    }
}