package org.example.marketing.exception

data class NotFoundAdminException(
    override val logics: String,
): NotFoundEntityException(
    logics = logics,
)
