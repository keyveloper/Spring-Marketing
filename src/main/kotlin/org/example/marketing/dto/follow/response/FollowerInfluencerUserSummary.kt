package org.example.marketing.dto.follow.response

import org.example.marketing.dto.user.response.InfluencerProfileInfo
import org.example.marketing.dto.user.response.UserProfileImageMetadataWithUrl
import java.util.UUID

data class FollowerInfluencerUserSummary(
    val influencerId: UUID?,
    val influencerName: String?,
    val influencerProfileImageUrl: String?,
    val job: String?
) {
    companion object {
        fun of(
            profileApiResult: InfluencerProfileInfo?,
            profileImageApiResult: UserProfileImageMetadataWithUrl?
        ): FollowerInfluencerUserSummary {
            return FollowerInfluencerUserSummary(
                influencerId = profileApiResult?.influencerId,
                influencerName = profileApiResult?.influencerName,
                influencerProfileImageUrl = profileImageApiResult?.presignedUrl,
                job = profileApiResult?.job
            )
        }
    }
}