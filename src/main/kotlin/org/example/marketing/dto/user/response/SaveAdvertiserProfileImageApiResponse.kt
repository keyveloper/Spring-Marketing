package org.example.marketing.dto.user.response

import org.example.marketing.enums.ProfileImageType

data class SaveAdvertiserProfileImageApiResponse(
    val metaEntityId: Long,
    val unifiedCode: String,
    val profileImageType: ProfileImageType
)