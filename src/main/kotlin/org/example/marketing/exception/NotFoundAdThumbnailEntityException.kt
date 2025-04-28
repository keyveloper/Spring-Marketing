package org.example.marketing.exception

data class NotFoundAdThumbnailEntityException(
    override val logics: String
): NotFoundEntityException(logics = logics)
