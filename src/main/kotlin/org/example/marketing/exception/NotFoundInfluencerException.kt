package org.example.marketing.exception

data class NotFoundInfluencerException(
    override val logics: String
): NotFoundEntityException(
    logics = logics
)