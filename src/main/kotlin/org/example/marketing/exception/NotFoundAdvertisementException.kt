package org.example.marketing.exception

data class NotFoundAdvertisementException(
    override val logics: String
): NotFoundEntityException(
    logics = logics
)
