package org.example.marketing.dto.keyword

data class GetScrappedDataRequest(
    val client: String,
    val keywords: List<String>
) {
    companion object {
        fun of(client: String,  keywords: List<String>): GetScrappedDataRequest {
            return GetScrappedDataRequest(client, keywords)
        }
    }
}