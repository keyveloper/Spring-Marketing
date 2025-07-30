package org.example.marketing.dto.user.response

import java.util.*

data class GetInfluencerProfileInfoResult(
    val id: Long,
    val userProfileDraftId: UUID,
    val influencerId: UUID,
    val introduction: String?,
    val job: String?,
    val createdAt: Long,
    val updatedAt: Long
)