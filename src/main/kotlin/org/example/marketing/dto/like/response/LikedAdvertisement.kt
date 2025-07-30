package org.example.marketing.dto.like.response

import org.example.marketing.enums.LikeStatus
import java.util.UUID

data class LikedAdvertisement(
    val id: Long,
    val influencerId: UUID,
    val advertisementId: Long,
    val likeStatus: LikeStatus,
    val createdAt: Long,
    val lastModifiedAt: Long
)
