package org.example.marketing.exception

data class DuplicatedInfluencerFavoriteAdException(
    override val message: String = "unique key error - influencerId - advertisementId",
    override val logics: String,
    val influencerId: Long,
    val advertisementId: Long
): DuplicatedException(logics = logics, message = message)



