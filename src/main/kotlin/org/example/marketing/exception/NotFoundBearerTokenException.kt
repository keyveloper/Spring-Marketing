package org.example.marketing.exception

import org.example.marketing.enums.FrontErrorCode
import org.springframework.http.HttpStatus

data class NotFoundBearerTokenException(
    override val httpStatus: HttpStatus = HttpStatus.UNAUTHORIZED,
    override val frontErrorCode: Int = FrontErrorCode.NOT_FOUND_BEARER.code,
    override val logics: String,
    override val message: String = FrontErrorCode.NOT_FOUND_BEARER.message
): BusinessException(httpStatus, frontErrorCode, logics, message)