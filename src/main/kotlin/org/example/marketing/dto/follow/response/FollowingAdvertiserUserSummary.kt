package org.example.marketing.dto.follow.response

import org.example.marketing.dto.user.response.AdvertiserProfileInfo
import org.example.marketing.dto.user.response.UserProfileImageMetadataWithUrl
import java.util.UUID

data class FollowingAdvertiserUserSummary(
    val advertiserId: UUID?,
    val advertiserName: String?,
    val advertiserProfileImageUrl: String?,
    val serviceInfo: String?
) {
    companion object {
        fun of(
            profileApiResult: AdvertiserProfileInfo?,
            profileImageApiResult: UserProfileImageMetadataWithUrl?
        ): FollowingAdvertiserUserSummary {
            return FollowingAdvertiserUserSummary(
                advertiserId = profileApiResult?.advertiserId,
                advertiserName = profileApiResult?.advertiserName,
                advertiserProfileImageUrl = profileImageApiResult?.presignedUrl,
                serviceInfo = profileApiResult?.serviceInfo
            )
        }
    }
}