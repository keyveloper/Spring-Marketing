package org.example.marketing.dto.user.response

import org.example.marketing.dao.user.AdvertiserProfileEntity

data class AdvertiserProfileResult(
    val companyInfo: String?,
    val companyLocation: String?,
    val introduction: String?,
    val followerCount: Long?,
) {
    companion object {
        fun of(
            advertiserProfileEntity: AdvertiserProfileEntity
        ): AdvertiserProfileResult {
            return AdvertiserProfileResult(
                companyInfo = advertiserProfileEntity.companyInfo,
                companyLocation = advertiserProfileEntity.companyLocation,
                introduction = advertiserProfileEntity.introduction,
                followerCount = 0L
            )
        }
    }
}