package org.example.marketing.exception

import org.example.marketing.enums.FrontErrorCode
import org.springframework.http.HttpStatus

class MissingUserNameException(
    override val httpStatus: HttpStatus = HttpStatus.BAD_REQUEST,
    override val frontErrorCode: Int = FrontErrorCode.MISSING_USER_NAME.code,
    override val message: String = FrontErrorCode.MISSING_USER_NAME.message,
    override val logics: String
) : BusinessException(
    httpStatus = httpStatus,
    frontErrorCode = frontErrorCode,
    message = message,
    logics = logics
)
