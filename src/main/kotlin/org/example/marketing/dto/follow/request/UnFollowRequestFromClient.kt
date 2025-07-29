package org.example.marketing.dto.follow.request

import java.util.UUID

data class UnFollowRequestFromClient(
    val advertiserId: UUID,
    val influencerId: UUID
)
