package org.example.marketing.dto.like.response

import java.util.UUID

data class CheckLikedAdsByInfluencerIdResult(
    val influencerId: UUID,
    val likedAdvertisements: List<LikedAdvertisement>
)
