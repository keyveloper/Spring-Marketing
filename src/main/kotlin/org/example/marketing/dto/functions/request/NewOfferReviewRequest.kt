package org.example.marketing.dto.functions.request

data class NewOfferReviewRequest(
    val advertisementId: Long,
    val offer: String,
)
