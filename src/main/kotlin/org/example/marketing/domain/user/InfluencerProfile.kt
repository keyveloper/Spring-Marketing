package org.example.marketing.domain.user

import java.util.*

data class InfluencerProfile(
    val userProfileDraftId: UUID,
    val influencerId: UUID,
    val introduction: String?,
    val job: String?
)
