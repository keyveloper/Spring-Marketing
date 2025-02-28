package org.example.marketing.exception

import org.springframework.http.HttpStatus

open class DuplicatedException(
    override val httpStatus: HttpStatus = HttpStatus.CONFLICT,
    override val logics: String,
    override val message: String,
): BusinessException(httpStatus, logics, message)