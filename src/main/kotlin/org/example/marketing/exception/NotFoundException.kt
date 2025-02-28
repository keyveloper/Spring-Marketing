package org.example.marketing.exception

import org.springframework.http.HttpStatus

open class NotFoundException(
    override val httpStatus: HttpStatus = HttpStatus.NOT_FOUND,
    override val logics: String,
    override val message: String,
    open val targetId: Long
): BusinessException(httpStatus, logics, message)