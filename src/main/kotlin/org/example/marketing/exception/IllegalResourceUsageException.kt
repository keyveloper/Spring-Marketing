package org.example.marketing.exception

import org.example.marketing.enums.FrontErrorCode
import org.springframework.http.HttpStatus

open class IllegalResourceUsageException(
    override val httpStatus: HttpStatus = HttpStatus.FORBIDDEN,
    override val frontErrorCode: Int = FrontErrorCode.UNAUTHORIZED_USER.code,
    override val message: String = FrontErrorCode.UNAUTHORIZED_USER.message,
    override val logics: String
): BusinessException(
    httpStatus = httpStatus,
    frontErrorCode = frontErrorCode,
    message = message,
    logics = logics
)
