package org.example.marketing.dto.keyword

data class GetKeywordCombinationRequest(
    val keyword: String,
    val context: String
)