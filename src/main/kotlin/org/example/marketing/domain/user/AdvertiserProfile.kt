package org.example.marketing.domain.user

import org.example.marketing.config.CustomDateTimeFormatter
import org.example.marketing.dao.user.AdvertiserProfileEntity

data class AdvertiserProfile(
    val id: Long,
    val advertiserId: Long,
    val companyInfo: String?,
    val companyLocation: String?,
    val introduction: String?,
    val createdAt: String,
    val updatedAt: String
) {
    companion object {
        fun of(entity: AdvertiserProfileEntity): AdvertiserProfile {
            return AdvertiserProfile(
                id = entity.id.value,
                advertiserId = entity.advertiserId,
                companyInfo = entity.companyInfo,
                companyLocation = entity.companyLocation,
                introduction = entity.introduction,
                createdAt = CustomDateTimeFormatter.epochToString(entity.createdAt),
                updatedAt = CustomDateTimeFormatter.epochToString(entity.updatedAt)
            )
        }
    }
}