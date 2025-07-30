package org.example.marketing.dto.user.response

import org.example.marketing.enums.ProfileImageType

data class GetAdvertiserProfileInfoWithImages(
    val profileApiResult: GetAdvertiserProfileInfoResult?,
    val profileImage: UserProfileImageMetadataWithUrl?,
    val backgroundImage: UserProfileImageMetadataWithUrl?,
) {
    companion object {
        fun of(
            profileApiResult: GetAdvertiserProfileInfoResult,
            profileImages: List<UserProfileImageMetadataWithUrl>
        ): GetAdvertiserProfileInfoWithImages {
            val profileImage = profileImages.find { it.profileImageType == ProfileImageType.PROFILE }
            val backgroundImage = profileImages.find { it.profileImageType == ProfileImageType.BACKGROUND }

            return GetAdvertiserProfileInfoWithImages(
                profileApiResult = profileApiResult,
                profileImage = profileImage,
                backgroundImage = backgroundImage
            )
        }
    }
}
