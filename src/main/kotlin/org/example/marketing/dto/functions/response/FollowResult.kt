package org.example.marketing.dto.functions.response

import org.example.marketing.enums.FollowStatus

data class FollowResult(
    val influencerId: Long,
    val advertiserId: Long,
    val followStatus: FollowStatus
) {
    companion object {
        fun of(
            influencerId: Long,
            advertiserId: Long,
            followStatus: FollowStatus
        ): FollowResult {
            return FollowResult(
                influencerId = influencerId,
                advertiserId = advertiserId,
                followStatus = followStatus
            )
        }
    }
}
