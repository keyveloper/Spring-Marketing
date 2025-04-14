package org.example.marketing.dto.keyword

data class NaverOpenApiParameter(
    val query: String,
    val display: Int?,
    val start: Int?,
    val sort: String?
)