package org.example.marketing.exception

import org.example.marketing.enums.FrontErrorCode

class UnLikeFailedException(
    override val logics: String,
    override val message: String = "Failed to unlike advertisement"
): MSAErrorException(
    frontErrorCode = FrontErrorCode.UNLIKE_FAILED.code,
    logics = logics,
    message = message
)
