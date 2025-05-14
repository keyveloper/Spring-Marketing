package org.example.marketing.dto.user.request

import org.example.marketing.enums.ProfileImageType
import org.example.marketing.enums.UserType

data class UploadAdvertiserProfileImageRequest(
    val userId: Long,
    val userType: UserType,
    val profileType: ProfileImageType
)
