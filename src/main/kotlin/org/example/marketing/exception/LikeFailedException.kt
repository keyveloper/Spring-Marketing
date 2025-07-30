package org.example.marketing.exception

import org.example.marketing.enums.FrontErrorCode

class LikeFailedException(
    override val logics: String,
    override val message: String = "Failed to like advertisement"
): MSAErrorException(
    frontErrorCode = FrontErrorCode.LIKE_FAILED.code,
    logics = logics,
    message = message
)
