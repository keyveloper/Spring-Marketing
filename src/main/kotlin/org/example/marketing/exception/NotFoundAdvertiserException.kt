package org.example.marketing.exception

data class NotFoundAdvertiserException(
    override val logics: String
): NotFoundEntityException(
    logics = logics
)
