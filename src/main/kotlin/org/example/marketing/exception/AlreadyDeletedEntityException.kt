package org.example.marketing.exception

import org.example.marketing.enums.FrontErrorCode
import org.springframework.http.HttpStatus

open class AlreadyDeletedEntityException(
    override val httpStatus: HttpStatus = HttpStatus.BAD_REQUEST,
    override val frontErrorCode: Int = FrontErrorCode.ALREADY_DELETED_ENTITY.code,
    override val logics: String,
    override val message: String = FrontErrorCode.ALREADY_DELETED_ENTITY.message,
): BusinessException(
    frontErrorCode = frontErrorCode,
    logics = logics,
    httpStatus = httpStatus,
    message = message
)