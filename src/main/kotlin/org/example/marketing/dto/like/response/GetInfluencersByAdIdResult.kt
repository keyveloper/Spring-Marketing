package org.example.marketing.dto.like.response

import java.util.UUID

data class GetInfluencersByAdIdResult(
    val advertisementId: Long,
    val influencerIds: List<UUID>
)
