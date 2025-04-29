package org.example.marketing.dto.functions.request


data class SaveReviewOffer(
    val advertisementId: Long,
    val influencerId: Long,
    val offer: String,
) {
    companion object {
        fun of(
            request: NewOfferReviewRequest,
            influencerId: Long
        ): SaveReviewOffer {
            return SaveReviewOffer(
                advertisementId = request.advertisementId,
                influencerId = influencerId,
                offer = request.offer,
            )
        }
    }
}
