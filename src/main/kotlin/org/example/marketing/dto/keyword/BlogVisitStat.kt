package org.example.marketing.dto.keyword

data class BlogVisitStat(
    val rank: Int,
    val title: String,
    val bloggerId: String?, // POST 떄문에 ... ㅊㅇ
    val blogUrl: String,
    val avg5dVisitCount: Int,
    val max5dVisitCount: Int,
    val min5dVisitCount: Int
)
