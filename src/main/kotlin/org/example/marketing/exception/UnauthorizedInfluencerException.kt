package org.example.marketing.exception

import org.example.marketing.enums.FrontErrorCode

data class UnauthorizedInfluencerException(
    override val frontErrorCode: Int = FrontErrorCode.UNAUTHORIZED_INFLUENCER.code,
    override val message: String = FrontErrorCode.UNAUTHORIZED_INFLUENCER.message,
    override val logics: String,
): UnauthorizedUserException(
    frontErrorCode = frontErrorCode,
    logics = logics,
    message = message
)