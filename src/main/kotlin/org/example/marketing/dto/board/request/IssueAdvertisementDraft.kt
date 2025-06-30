package org.example.marketing.dto.board.request

data class IssueAdvertisementDraft(
    val advertiserId: String,
) {
    companion object {
        fun of(advertiserId: String): IssueAdvertisementDraft =
            IssueAdvertisementDraft(advertiserId = advertiserId)
    }
}
