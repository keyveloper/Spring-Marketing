package org.example.marketing.exception

data class DuplicatedReviewOfferException(
    override val logics: String,
    val influencerId: Long,
    val advertisementId: Long
): DuplicatedException(logics = logics)
