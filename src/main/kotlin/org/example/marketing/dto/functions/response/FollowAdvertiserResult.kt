package org.example.marketing.dto.functions.response

import org.example.marketing.enums.FollowStatus

data class FollowAdvertiserResult(
    val influencerId: Long,
    val advertiserId: Long,
    val followStatus: FollowStatus
) {
    companion object {
        fun of(
            influencerId: Long,
            advertiserId: Long,
            followStatus: FollowStatus
        ): FollowAdvertiserResult {
            return FollowAdvertiserResult(
                influencerId = influencerId,
                advertiserId = advertiserId,
                followStatus = followStatus
            )
        }
    }
}
