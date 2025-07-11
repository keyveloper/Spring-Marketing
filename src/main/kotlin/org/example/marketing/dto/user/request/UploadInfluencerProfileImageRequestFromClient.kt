package org.example.marketing.dto.user.request

import org.example.marketing.enums.ProfileImageType
import java.util.UUID

data class UploadInfluencerProfileImageRequestFromClient(
    val influencerProfileDraftId: UUID,
    val profileImageType: ProfileImageType
)