package org.example.marketing.dto.naver

data class NaverOpenApiParameter(
    val query: String,
    val display: Int?,
    val start: Int?,
    val sort: String?
)