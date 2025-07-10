package org.example.marketing.dto.user.request

import org.example.marketing.enums.ProfileImageType

data class UploadProfileImageRequestFromClient(
    val profileImageType: ProfileImageType
)
