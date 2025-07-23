package org.example.marketing.dto.like.response

import org.example.marketing.enums.LikeStatus
import java.time.LocalDateTime
import java.util.UUID

data class LikeAd(
    val influencerId: UUID,
    val advertisementId: Long,
    val likeStatus: LikeStatus,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
)
