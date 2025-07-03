package org.example.marketing.exception

import org.example.marketing.enums.FrontErrorCode
import org.example.marketing.enums.ReviewType
import org.springframework.http.HttpStatus

open class UnexpectedDeliveryCategoryException(
    override val httpStatus: HttpStatus = HttpStatus.BAD_REQUEST,
    override val frontErrorCode: Int = FrontErrorCode.UNEXPECTED_DELIVERY_CATEGORY.code,
    override val message: String = FrontErrorCode.UNEXPECTED_DELIVERY_CATEGORY.message,
    override val logics: String,
    val reviewType: ReviewType,
): BusinessException(httpStatus, frontErrorCode, logics, message)