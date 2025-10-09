package org.example.marketing.dto.user.request

import org.example.marketing.enums.ProfileImageType
import org.example.marketing.enums.UserType

data class UploadUserProfileImageApiRequest(
    val userType: UserType,
    val userId: Long,
    val profileImageType: ProfileImageType
) {
    companion object {
        fun of(
            userType: UserType,
            userId: Long,
            profileImageType: ProfileImageType
        ): UploadUserProfileImageApiRequest {
            return UploadUserProfileImageApiRequest(
                userType, userId, profileImageType
            )
        }
    }
}