package org.example.marketing.exception

import org.springframework.http.HttpStatus

data class DuplicatedAdminException(
    override val logics: String,
    override val message: String = "this admin loginId is duplicated...",
    val duplicatedLoginId: String,
): DuplicatedException(logics = logics, message = message)