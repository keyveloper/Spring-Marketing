package org.example.marketing.dto.keyword

data class RelatedKeywordStat(
    val relKeyword: String,
    val compIdx: String,
    val monthlyPcQcCnt: String,
    val monthlyMobileQcCnt: String,
    val monthlyAvePcClkCnt: String,
    val monthlyAveMobileClkCnt: String,
    val monthlyAvePcCtr: String,
    val monthlyAveMobileCtr: String,
    val plAvgDepth: String
)
