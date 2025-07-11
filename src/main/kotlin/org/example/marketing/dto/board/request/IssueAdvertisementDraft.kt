package org.example.marketing.dto.board.request

import java.util.UUID

data class IssueAdvertisementDraft(
    val advertiserId: UUID,
) {
    companion object {
        fun of(advertiserId: UUID): IssueAdvertisementDraft =
            IssueAdvertisementDraft(advertiserId = advertiserId)
    }
}
