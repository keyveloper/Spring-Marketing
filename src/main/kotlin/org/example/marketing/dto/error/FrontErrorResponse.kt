package org.example.marketing.dto.error

import org.example.marketing.enum.FrontErrorCode

open class FrontErrorResponse(
    open val frontErrorCode: Int = FrontErrorCode.OK.code,
    open val errorMessage: String = (FrontErrorCode.OK.message
    // need security
)