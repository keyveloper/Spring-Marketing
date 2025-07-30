package org.example.marketing.dto.user.request

import java.util.*

data class UpdateAdvertiserProfileInfoApiRequest(
    val advertiserId: UUID,
    val userProfileDraftId: UUID,
    val serviceInfo: String,
    val locationBrief: String,
    val introduction: String?
)
