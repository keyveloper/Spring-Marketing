package org.example.marketing.dto.like.request

import java.util.UUID

data class LikeOrSwitchApiRequest(
    val influencerId: UUID,
    val advertisementId: Long
)
