package org.example.marketing.exception

import org.example.marketing.enums.FrontErrorCode
import org.springframework.http.HttpStatus

data class InvalidJwtTokenException(
    override val httpStatus: HttpStatus = HttpStatus.UNAUTHORIZED,
    override val frontErrorCode: Int = FrontErrorCode.INVALID_JWT_TOKEN.code,
    override val logics: String,
    override val message: String = FrontErrorCode.INVALID_JWT_TOKEN.message,
    val jwtToken: String,
): BusinessException(httpStatus, frontErrorCode, logics, message)