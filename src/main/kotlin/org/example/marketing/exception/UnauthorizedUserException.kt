package org.example.marketing.exception

import org.example.marketing.enums.FrontErrorCode
import org.springframework.http.HttpStatus

open class UnauthorizedUserException(
    override val httpStatus: HttpStatus = HttpStatus.FORBIDDEN,
    override val frontErrorCode: Int,
    override val message: String,
    override val logics: String
): BusinessException(
    frontErrorCode = frontErrorCode,
    message = message,
    httpStatus = httpStatus,
    logics = logics
)
