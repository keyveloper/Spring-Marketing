package org.example.marketing.exception

import org.example.marketing.enums.FrontErrorCode
import org.springframework.http.HttpStatus

data class NotMatchedUserTypeException(
    override val httpStatus: HttpStatus = HttpStatus.FORBIDDEN,
    override val frontErrorCode: Int = FrontErrorCode.NOT_MATCHED_EXPECTED_USER_TYPE.code, // FrontErrorCode
    override val message: String = FrontErrorCode.NOT_MATCHED_EXPECTED_USER_TYPE.message,
    override val logics: String ,
    ): BusinessException(
        httpStatus = httpStatus,
        frontErrorCode = frontErrorCode,
        message = message,
        logics = logics
    )