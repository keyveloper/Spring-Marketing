package org.example.marketing.dto.like.request

import java.util.UUID

data class CheckLikedAdsByInfluencerIdApiRequest(
    val influencerId: UUID,
    val advertisementIds: List<Long>
)
