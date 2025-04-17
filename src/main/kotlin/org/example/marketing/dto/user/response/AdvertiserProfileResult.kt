package org.example.marketing.dto.user.response

import org.example.marketing.dao.user.AdvertiserEntity
import org.example.marketing.dao.user.AdvertiserProfileEntity
import org.example.marketing.repository.user.AdvertiserProfileRepository

data class AdvertiserProfileResult(
    val advertiserId: Long,
    val companyName: String,
    val companyInfo: String?,
    val companyLocation: String?,
    val introduction: String?,
    val followerCount: Long?,
) {
    companion object {
        fun of(
            advertiserEntity: AdvertiserEntity,
            advertiserProfileEntity: AdvertiserProfileEntity
        ): AdvertiserProfileResult {
            return AdvertiserProfileResult(
                advertiserId = advertiserEntity.id.value,
                companyName = advertiserEntity.companyName,
                companyInfo = advertiserProfileEntity.companyInfo,
                companyLocation = advertiserProfileEntity.companyLocation,
                introduction = advertiserProfileEntity.introduction,
                followerCount = 0L
            )
        }
    }
}