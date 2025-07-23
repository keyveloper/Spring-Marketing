package org.example.marketing.dto.follow.request

import java.util.UUID

data class FollowOrSwitchApiRequest(
    val advertiserId: UUID,
    val influencerId: UUID
)
