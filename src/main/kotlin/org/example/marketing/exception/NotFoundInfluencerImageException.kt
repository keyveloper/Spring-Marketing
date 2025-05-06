package org.example.marketing.exception

data class NotFoundInfluencerImageException(
    override val logics: String
): NotFoundEntityException(
    logics = logics
)
