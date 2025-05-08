package org.example.marketing.dto.user.request

import org.example.marketing.enums.ProfileImageType

data class MakeNewAdvertiserProfileImageRequest(
    val originalFileName: String,
    val profileImageType: ProfileImageType
)
