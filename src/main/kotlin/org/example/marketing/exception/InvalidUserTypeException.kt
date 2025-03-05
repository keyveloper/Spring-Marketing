package org.example.marketing.exception

import org.example.marketing.enum.FrontErrorCode
import org.springframework.http.HttpStatus

data class InvalidUserTypeException(
    override val httpStatus: HttpStatus = HttpStatus.BAD_REQUEST,
    override val frontErrorCode: Int = FrontErrorCode.INVALID_USER_TYPE.code,
    override val logics: String,
    override val message: String = FrontErrorCode.INVALID_USER_TYPE.message
): BusinessException(httpStatus, frontErrorCode, logics, message)
