package org.example.marketing.dto.error

import org.example.marketing.enum.FrontErrorCode

open class FrontErrorResponse(
    open val frontErrorCode: Int,
    open val errorMessage: String
    // need security
)