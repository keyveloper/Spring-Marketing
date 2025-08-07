package org.example.marketing.dto.follow.response

import org.example.marketing.enums.FollowStatus
import java.time.LocalDateTime
import java.util.UUID

data class FollowResult(
    val advertiserId: UUID,
    val influencerId: UUID,
    val followStatus: FollowStatus,
    val createdAt: Long?,
    val updatedAt: Long?
) {
    companion object {
        fun of(fromServer: FollowAdvertiser): FollowResult {
            return FollowResult(
                advertiserId = fromServer.advertiserId,
                influencerId = fromServer.influencerId,
                followStatus = fromServer.followStatus,
                createdAt = fromServer.createdAt,
                updatedAt = fromServer.updatedAt
            )
        }
    }
}
