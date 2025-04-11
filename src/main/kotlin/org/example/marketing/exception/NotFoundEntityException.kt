package org.example.marketing.exception

import org.example.marketing.enums.FrontErrorCode
import org.springframework.http.HttpStatus

open class NotFoundEntityException(
    override val httpStatus: HttpStatus = HttpStatus.NOT_FOUND,
    override val frontErrorCode: Int = FrontErrorCode.NOT_FOUND_ENTITY.code,
    override val logics: String,
    override val message: String = FrontErrorCode.NOT_FOUND_ENTITY.message,
): BusinessException(httpStatus, frontErrorCode, logics, message)