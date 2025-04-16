package org.example.marketing.domain.user

import org.example.marketing.config.CustomDateTimeFormatter
import org.example.marketing.dao.user.AdvertiserEntity
import org.example.marketing.enums.AdvertiserType
import org.example.marketing.enums.UserStatus

data class Advertiser(
    val id: Long,
    val loginId: String,
    val password: String,
    val status: UserStatus,
    val email: String,
    val contact: String,
    val homepageUrl: String?,
    val advertiserType: AdvertiserType,
    val companyName: String,
    val createdAt: String,
    val updatedAt: String
) {
    companion object {
        fun of(entity: AdvertiserEntity): Advertiser {
            return Advertiser(
                id = entity.id.value,
                loginId = entity.loginId,
                password = entity.password,
                status = entity.status,
                email = entity.email,
                contact = entity.contact,
                homepageUrl = entity.homepageUrl,
                advertiserType = entity.advertiserType,
                companyName = entity.companyName,
                createdAt = CustomDateTimeFormatter.epochToString(entity.createdAt),
                updatedAt = CustomDateTimeFormatter.epochToString(entity.updatedAt)
            )
        }
    }
}