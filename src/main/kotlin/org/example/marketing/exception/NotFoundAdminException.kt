package org.example.marketing.exception

import org.springframework.http.HttpStatus

data class NotFoundAdminException(
    override val logics: String,
): NotFoundException(
    logics = logics,
)
