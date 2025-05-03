package org.example.marketing.dto.board.request

data class IssueAdvertisementDraft(
    val advertiserId: Long,
) {
    companion object {
        fun of(advertiserId: Long): IssueAdvertisementDraft =
            IssueAdvertisementDraft(advertiserId = advertiserId)
    }
}
