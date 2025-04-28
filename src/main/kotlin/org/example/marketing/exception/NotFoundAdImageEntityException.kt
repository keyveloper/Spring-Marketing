package org.example.marketing.exception

import org.example.marketing.enums.FrontErrorCode
import org.springframework.http.HttpStatus

data class NotFoundAdImageEntityException(
    override val logics: String
): NotFoundEntityException(
    logics = logics
)

