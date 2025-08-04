package org.example.marketing.dto.user.response

import java.util.UUID

data class InfluencerProfileInfo(
    val id: Long,
    val userProfileDraftId: UUID,
    val influencerName: String,
    val influencerId: UUID,
    val introduction: String?,
    val job: String?,
    val createdAt: Long,
    val updatedAt: Long
)
