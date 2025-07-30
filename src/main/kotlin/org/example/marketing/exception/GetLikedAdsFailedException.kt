package org.example.marketing.exception

import org.example.marketing.enums.FrontErrorCode

class GetLikedAdsFailedException(
    override val logics: String,
    override val message: String = "Failed to get liked advertisements"
): MSAErrorException(
    frontErrorCode = FrontErrorCode.GET_LIKED_ADS_FAILED.code,
    logics = logics,
    message = message
)
