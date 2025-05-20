package org.example.marketing.dto.functions.request

import jakarta.validation.constraints.NotEmpty

data class NewOfferReviewRequest(
    val advertisementId: Long,

    @field:NotEmpty
    val offer: String,
)
