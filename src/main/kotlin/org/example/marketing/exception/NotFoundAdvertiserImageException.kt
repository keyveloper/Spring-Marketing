package org.example.marketing.exception

data class NotFoundAdvertiserImageException(
    override val logics: String
): NotFoundEntityException(
    logics = logics
)
