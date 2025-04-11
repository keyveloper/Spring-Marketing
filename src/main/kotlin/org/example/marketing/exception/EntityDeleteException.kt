package org.example.marketing.exception

import org.example.marketing.enums.FrontErrorCode
import org.springframework.http.HttpStatus

data class EntityDeleteException(
    override val httpStatus: HttpStatus = HttpStatus.BAD_REQUEST,
    override val frontErrorCode: Int = FrontErrorCode.CANNOT_DELETE_ENTITY.code,
    override val logics: String,
    override val message: String = FrontErrorCode.CANNOT_DELETE_ENTITY.message,
): BusinessException(httpStatus, frontErrorCode, logics, message)
