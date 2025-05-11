package org.example.marketing.dto.keyword

data class GetScrappedStataRequestToScrapperServer(
    val client: String,
    val keyword: String
) {
    companion object {
        fun of(client: String,  keyword: String): GetScrappedStataRequestToScrapperServer {
            return GetScrappedStataRequestToScrapperServer(client, keyword)
        }
    }
}