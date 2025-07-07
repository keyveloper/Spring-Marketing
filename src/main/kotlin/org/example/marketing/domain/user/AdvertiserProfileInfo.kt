package org.example.marketing.domain.user

import org.example.marketing.dao.user.AdvertiserJoinedProfileEntity

data class AdvertiserProfileInfo(
    val profileUnifiedCode: String,
    val backgroundUnifiedCode: String,
    val serviceInfo: String?,
    val locationBrief: String?,
    val introduction: String?
) {
    companion object {
        fun of(
            entity: AdvertiserJoinedProfileEntity,
            profileUnifiedCode: String,
            backgroundUnifiedCode: String,
        ): AdvertiserProfileInfo {
            return AdvertiserProfileInfo(
                serviceInfo = entity.serviceInfo,
                locationBrief = entity.locationBrief,
                introduction = entity.introduction,
                profileUnifiedCode = profileUnifiedCode,
                backgroundUnifiedCode = backgroundUnifiedCode,
            )
        }
    }
}