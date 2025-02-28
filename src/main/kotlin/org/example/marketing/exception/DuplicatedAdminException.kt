package org.example.marketing.exception

import org.springframework.http.HttpStatus

data class DuplicatedAdminException(
    override val httpStatus: HttpStatus = HttpStatus.CONFLICT,
    override val logics: String,
    override val message: String = "this loginId is duplicated...",
    val duplicatedLoginId: String,
): DuplicatedException(httpStatus, logics, message)