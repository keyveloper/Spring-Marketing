package org.example.marketing.domain.user

import org.example.marketing.dao.user.AdvertiserJoinedProfileEntity

data class AdvertiserProfileInfo(
    val advertiserId: Long,
    val advertiserLoginId: String,
    val companyName: String,
    val email: String,
    val contact: String,
    val homepageUrl: String?,
    val createdAt: Long,
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
                advertiserId = entity.advertiserId,
                advertiserLoginId = entity.advertiserLoginId,
                companyName = entity.companyName,
                email = entity.email,
                contact = entity.contact,
                homepageUrl = entity.homepageUrl,
                createdAt = entity.createdAt,
                serviceInfo = entity.serviceInfo,
                locationBrief = entity.locationBrief,
                introduction = entity.introduction,
                profileUnifiedCode = profileUnifiedCode,
                backgroundUnifiedCode = backgroundUnifiedCode,
            )
        }
    }
}