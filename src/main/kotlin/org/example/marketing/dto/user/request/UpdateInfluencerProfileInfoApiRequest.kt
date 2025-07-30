package org.example.marketing.dto.user.request

import java.util.*

data class UpdateInfluencerProfileInfoApiRequest(
    val userProfileDraftId: UUID,
    val influencerId: UUID,
    val introduction: String?,
    val job: String?
)
