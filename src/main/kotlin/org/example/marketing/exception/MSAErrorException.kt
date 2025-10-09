package org.example.marketing.exception

import org.example.marketing.enums.FrontErrorCode
import org.springframework.http.HttpStatus

open class MSAErrorException(
    override val httpStatus: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
    override val frontErrorCode: Int = FrontErrorCode.UNEXPECTED_MSA_SERVER_ERROR.code,
    override val logics: String = "[service] - {method}(this message should be overrode by child)",
    override val message: String = "This is a basic msa server error message!"
): BusinessException(httpStatus, frontErrorCode, logics, message)
