package org.example.marketing.exception

import org.springframework.http.HttpStatus

open class BusinessException(
    open val httpStatus: HttpStatus,
    open val logics: String,
    override val message: String,
    ): RuntimeException(message)