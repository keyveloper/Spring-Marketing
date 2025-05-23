package org.example.marketing.exception

import org.example.marketing.enums.FrontErrorCode
import org.springframework.http.HttpStatus

data class NotMatchedPasswordException(
    override val httpStatus: HttpStatus = HttpStatus.BAD_REQUEST,
    override val frontErrorCode: Int = FrontErrorCode.PASSWORD_NOT_MATCHER.code,
    override val message: String = FrontErrorCode.PASSWORD_NOT_MATCHER.message,
    override val logics: String
): BusinessException(httpStatus, frontErrorCode, logics, message)