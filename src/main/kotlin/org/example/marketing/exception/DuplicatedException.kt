package org.example.marketing.exception

import org.example.marketing.enums.FrontErrorCode
import org.springframework.http.HttpStatus

open class DuplicatedException(
    override val httpStatus: HttpStatus = HttpStatus.CONFLICT,
    override val frontErrorCode: Int = FrontErrorCode.DUPLICATED.code,
    override val logics: String,
    override val message: String = FrontErrorCode.DUPLICATED.message,
): BusinessException(httpStatus, frontErrorCode, logics, message)