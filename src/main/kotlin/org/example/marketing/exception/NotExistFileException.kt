package org.example.marketing.exception

import org.example.marketing.enums.FrontErrorCode
import org.springframework.http.HttpStatus

data class NotExistFileException(
    override val httpStatus: HttpStatus = HttpStatus.NOT_FOUND,
    override val frontErrorCode: Int = FrontErrorCode.NOT_FOUND_FILE.code,
    override val message: String = FrontErrorCode.NOT_FOUND_FILE.message,
    override val logics: String,
    val filePath: String
): BusinessException(
    httpStatus,
    frontErrorCode,
    message,
    logics,
)
