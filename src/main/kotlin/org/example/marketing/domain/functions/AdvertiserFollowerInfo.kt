package org.example.marketing.domain.functions

import org.example.marketing.enums.FollowStatus

data class AdvertiserFollowerInfo(
    val requestingInfluencerId: Long,
    val requestingInfluencerFollowStatus: FollowStatus?,
    val targetAdvertiserId: Long,
    val targetAdvertiserFollowerCount: Long
) {
    companion object {
        fun of(
            requestingInfluencerId: Long,
            requestingInfluencerFollowStatus: FollowStatus?,
            targetAdvertiserId: Long,
            targetAdvertiserFollowerCount: Long
        ): AdvertiserFollowerInfo
        = AdvertiserFollowerInfo(
            requestingInfluencerId,
            requestingInfluencerFollowStatus,
            targetAdvertiserId,
            targetAdvertiserFollowerCount
        )
    }
}
