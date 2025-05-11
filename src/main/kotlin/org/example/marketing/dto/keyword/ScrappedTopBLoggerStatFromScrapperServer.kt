package org.example.marketing.dto.keyword

data class ScrappedTopBLoggerStatFromScrapperServer(
    val keyword: String,
    val top10BlogVisitStat: List<BlogVisitStat>,
)