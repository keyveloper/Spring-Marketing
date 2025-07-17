package org.example.marketing.exception

import java.util.UUID

data class DuplicatedReviewApplicationException(
    override val logics: String,
    val influencerId: UUID,
    val advertisementId: Long
): DuplicatedException(logics = logics)
