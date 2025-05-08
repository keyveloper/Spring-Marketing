package org.example.marketing.dao.user

import org.example.marketing.enums.ProfileImageType
import org.example.marketing.table.AdvertiserProfileImagesTable
import org.example.marketing.table.AdvertiserProfileInfosTable
import org.example.marketing.table.AdvertisersTable
import org.jetbrains.exposed.sql.ResultRow

data class AdvertiserJoinedProfileEntity(
    val advertiserId: Long,
    val advertiserLoginId: String,
    val companyName: String,
    val email: String,
    val contact: String,
    val homepageUrl: String?,
    val createdAt: Long ,
    val unifiedImageCode: String,
    val profileImageType: ProfileImageType,
    val serviceInfo: String,
    val locationBrief: String,
    val introduction: String?
) {
    companion object {
        fun fromRow(row: ResultRow): AdvertiserJoinedProfileEntity {
            return AdvertiserJoinedProfileEntity(
                advertiserId = row[AdvertisersTable.id].value,
                advertiserLoginId = row[AdvertisersTable.loginId],
                companyName = row[AdvertisersTable.companyName],
                email = row[AdvertisersTable.email],
                contact = row[AdvertisersTable.contact],
                homepageUrl = row[AdvertisersTable.homepageUrl],
                createdAt = row[AdvertisersTable.createdAt],
                unifiedImageCode = row[AdvertiserProfileImagesTable.unifiedCode],
                profileImageType = row[AdvertiserProfileImagesTable.profileImageType],
                serviceInfo = row[AdvertiserProfileInfosTable.serviceInfo],
                locationBrief = row[AdvertiserProfileInfosTable.locationBrief],
                introduction = row[AdvertiserProfileInfosTable.introduction]
            )
        }
    }
}
