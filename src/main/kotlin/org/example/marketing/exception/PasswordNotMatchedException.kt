package org.example.marketing.exception

import org.example.marketing.enum.FrontErrorCode
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.BadCredentialsException

data class PasswordNotMatchedException(
    override val httpStatus: HttpStatus = HttpStatus.BAD_REQUEST,
    override val frontErrorCode: Int = FrontErrorCode.PASSWORD_NOT_MATCHER.code,
    override val message: String = FrontErrorCode.PASSWORD_NOT_MATCHER.message,
    override val logics: String
): BusinessException(httpStatus, frontErrorCode, logics, message)