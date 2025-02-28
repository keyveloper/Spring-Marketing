package org.example.marketing.exception

import org.springframework.http.HttpStatus

data class NotFoundAdminException(
    override val httpStatus: HttpStatus = HttpStatus.NOT_FOUND,
    override val logics: String,
    override val message: String = "can't find ths admin user...",
    override val targetId: Long,
): NotFoundException(httpStatus, logics, message, targetId)
