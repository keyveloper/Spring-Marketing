package org.example.marketing.exception

import org.example.marketing.enums.FrontErrorCode
import org.springframework.http.HttpStatus

open class ExpiredDraftException(
    override val httpStatus: HttpStatus = HttpStatus.GONE,
    override val frontErrorCode: Int = FrontErrorCode.EXPIRED_DRAFT_EXCEPTION.code,
    override val message: String = FrontErrorCode.EXPIRED_DRAFT_EXCEPTION.message,
    override val logics: String,
    val expiredAt: String,
    val apiCallAt: String,
): BusinessException(httpStatus, frontErrorCode, message, logics)
