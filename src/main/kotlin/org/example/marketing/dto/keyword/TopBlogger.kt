package org.example.marketing.dto.keyword

data class TopBlogger(
    val title: String,
    val bloggerId: String,
    val postingUrl: String,
    val visitStatAvg5: Int,
    val visitStatMin5: Int,
    val visitStatMax5: Int
)
