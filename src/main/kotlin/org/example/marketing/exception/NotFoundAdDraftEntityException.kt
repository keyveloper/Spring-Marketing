package org.example.marketing.exception

data class NotFoundAdDraftEntityException(
    override val logics: String
): NotFoundEntityException(logics = logics)
