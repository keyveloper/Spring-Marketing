package org.example.marketing.dto.like.request

import java.util.UUID

data class LikeOrSwitchRequestFromClient(
    val influencerId: UUID,
    val advertisementId: Long
)
