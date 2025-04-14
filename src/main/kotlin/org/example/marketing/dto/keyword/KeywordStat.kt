package org.example.marketing.dto.keyword

data class KeywordStat(
    val monthlySearchVolumePc: Int,
    val monthlySearchVolumeMobile: Int,
    val MKEI: Double,
    val MKGR: Double,
    val competition: String,
    val top5Blogger: List<TopBlogger>
)