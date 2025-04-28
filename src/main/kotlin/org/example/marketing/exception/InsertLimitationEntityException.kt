package org.example.marketing.exception

import org.example.marketing.enums.FrontErrorCode
import org.springframework.http.HttpStatus

open class InsertLimitationEntityException(
    override val httpStatus: HttpStatus = HttpStatus.SERVICE_UNAVAILABLE,
    override val frontErrorCode: Int = FrontErrorCode.INSERT_LIMIT_ENTITY.code,
    override val message: String = FrontErrorCode.INSERT_LIMIT_ENTITY.message,
    override val logics: String,
    open val policy: String
): BusinessException(
    frontErrorCode = frontErrorCode,
    message = message,
    httpStatus = httpStatus,
    logics = logics
)
