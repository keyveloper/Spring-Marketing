package org.example.marketing.dto.follow.response

import org.example.marketing.enums.FollowStatus
import java.time.LocalDateTime
import java.util.UUID

data class FollowResultFromServer(
    val advertiserId: UUID,
    val influencerId: UUID,
    val followStatus: FollowStatus,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
)
