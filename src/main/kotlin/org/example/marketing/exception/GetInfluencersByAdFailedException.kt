package org.example.marketing.exception

import org.example.marketing.enums.FrontErrorCode

class GetInfluencersByAdFailedException(
    override val logics: String,
    override val message: String = "Failed to get influencers by advertisement"
): MSAErrorException(
    frontErrorCode = FrontErrorCode.GET_INFLUENCERS_BY_AD_FAILED.code,
    logics = logics,
    message = message
)
