package org.example.marketing.domain.user

import java.util.*

data class AdvertiserProfile(
    val advertiserId: UUID,
    val userProfileDraftId: UUID,
    val serviceInfo: String,
    val locationBrief: String,
    val introduction: String?
)
