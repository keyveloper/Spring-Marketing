package org.example.marketing.domain.user

import org.example.marketing.dao.user.AdvertiserProfileImageEntity
import org.example.marketing.enums.ProfileImageType

data class AdvertiserProfileImage(
    val metaEntityId: Long,
    val unifiedCode: String,
    val profileImageType: ProfileImageType
) {
    companion object {
        fun of(entity: AdvertiserProfileImageEntity): AdvertiserProfileImage =
            AdvertiserProfileImage(
                metaEntityId = entity.id.value,
                unifiedCode = entity.unifiedCode,
                profileImageType = entity.profileImageType
            )
        }
}