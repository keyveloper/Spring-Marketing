package org.example.marketing.dto.follow.request

import java.util.UUID

data class UnFollowApiRequest(
    val advertiserId: UUID,
    val influencerId: UUID
)
