package org.example.marketing.dto.like.response

import java.util.UUID

data class GetLikedAdsByInfluencerIdResult(
    val influencerId: UUID,
    val advertisementIds: List<Long>
)
