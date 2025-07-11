package org.example.marketing.dto.user.request

import org.example.marketing.enums.ProfileImageType
import org.example.marketing.enums.UserType
import java.util.UUID

data class UploadUserProfileImageApiRequest(
    val userId: UUID,
    val userType: UserType,
    val profileImageType: ProfileImageType,
    val userProfileDraftId: UUID
) {
    companion object {
        fun of(
            userId: UUID,
            userType: UserType,
            profileImageType: ProfileImageType,
            userProfileDraftId: UUID
        ): UploadUserProfileImageApiRequest {
            return UploadUserProfileImageApiRequest(
                 userId, userType, profileImageType, userProfileDraftId
            )
        }
    }
}