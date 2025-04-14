package org.example.marketing.dto.keyword

data class BlogVisitStat(
    private val keyword: String,
    private val title: String,
    private val avg5dCount: Int,
    private val maxCount: Int,
    private val minCount: Int
)
