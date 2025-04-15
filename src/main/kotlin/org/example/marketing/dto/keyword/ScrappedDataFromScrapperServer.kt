package org.example.marketing.dto.keyword

data class ScrappedDataFromScrapperServer(
    val keyword: String,
    val blogVisitStat: List<BlogVisitStat>,
    val monthlyMeaningfulPostingBlogCount: Int,
)