package org.example.marketing.dto.board.request

data class IssueAdvertisementDraft(
    val advertiserId: Long,
) {
    companion object {
        fun of(request: IssueNewAdvertisementDraftRequest): IssueAdvertisementDraft =
            IssueAdvertisementDraft(advertiserId = request.advertiserId)
    }
}
