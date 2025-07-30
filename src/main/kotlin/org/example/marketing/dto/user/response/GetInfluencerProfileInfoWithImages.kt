package org.example.marketing.dto.user.response

import org.example.marketing.enums.ProfileImageType

data class GetInfluencerProfileInfoWithImages(
    val profileApiResult: GetInfluencerProfileInfoResult?,
    val profileImage: UserProfileImageMetadataWithUrl?,
    val backgroundImage: UserProfileImageMetadataWithUrl?,
) {
    companion object {
        fun of(
            profileApiResult: GetInfluencerProfileInfoResult,
            profileImages: List<UserProfileImageMetadataWithUrl>
        ): GetInfluencerProfileInfoWithImages {
            val profileImage = profileImages.find { it.profileImageType == ProfileImageType.PROFILE }
            val backgroundImage = profileImages.find { it.profileImageType == ProfileImageType.BACKGROUND }

            return GetInfluencerProfileInfoWithImages(
                profileApiResult = profileApiResult,
                profileImage = profileImage,
                backgroundImage = backgroundImage
            )
        }
    }
}