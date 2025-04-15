package org.example.marketing.dto.keyword

data class BlogVisitStat(
    private val title: String,
    private val bloggerId: String,
    private val blogUrl: String,
    private val rank: Int,
    private val avg5dVisitCount: Int,
    private val max5dVisitCount: Int,
    private val min5dVisitCount: Int
)
