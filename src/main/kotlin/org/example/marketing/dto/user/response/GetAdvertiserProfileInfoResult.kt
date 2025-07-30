package org.example.marketing.dto.user.response

import java.util.*

data class GetAdvertiserProfileInfoResult(
    val advertiserId: UUID,
    val profilePresignedUrl: String,
    val backgroundPresignedUrl: String,
    val userProfileDraftId: UUID,
    val serviceInfo: String,
    val locationBrief: String,
    val introduction: String?
)
