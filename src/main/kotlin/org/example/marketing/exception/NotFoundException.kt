package org.example.marketing.exception

import org.example.marketing.enum.FrontErrorCode
import org.springframework.http.HttpStatus

open class NotFoundException(
    override val httpStatus: HttpStatus = HttpStatus.NOT_FOUND,
    override val frontErrorCode: Int = FrontErrorCode.NOT_FOUND_ENTITY.code,
    override val logics: String,
    override val message: String,
    open val targetId: Long
): BusinessException(httpStatus, frontErrorCode, logics, message)