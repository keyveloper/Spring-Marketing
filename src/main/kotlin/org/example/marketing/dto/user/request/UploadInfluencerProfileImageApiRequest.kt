package org.example.marketing.dto.user.request

import org.example.marketing.enums.ProfileImageType

data class UploadInfluencerProfileImageApiRequest(
    val userId: String,
    val userType: String,
    val influencerProfileDraftId: String,
    val profileImageType: ProfileImageType
)
