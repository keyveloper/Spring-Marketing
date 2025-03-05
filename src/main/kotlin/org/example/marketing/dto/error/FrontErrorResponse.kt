package org.example.marketing.dto.error


open class FrontErrorResponse(
    open val frontErrorCode: Int,
    open val errorMessage: String
    // need security
)