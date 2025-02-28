package org.example.marketing.exception

import org.springframework.http.HttpStatus
import kotlin.math.log

open class DuplicatedException(
    override val httpStatus: HttpStatus = HttpStatus.CONFLICT,
    override val logics: String,
    override val message: String,
): BusinessException(httpStatus, logics, message)
