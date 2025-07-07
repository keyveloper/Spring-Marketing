package org.example.marketing.dao.user

import org.example.marketing.enums.ProfileImageType
import org.example.marketing.table.AdvertiserProfileImagesTable
import org.example.marketing.table.AdvertiserProfileInfosTable
import org.jetbrains.exposed.sql.ResultRow

data class AdvertiserJoinedProfileEntity(

    val unifiedImageCode: String,
    val profileImageType: ProfileImageType,
    val serviceInfo: String?,
    val locationBrief: String?,
    val introduction: String?
) {
    companion object {
        fun fromRow(row: ResultRow): AdvertiserJoinedProfileEntity {
            return AdvertiserJoinedProfileEntity(

                unifiedImageCode = row[AdvertiserProfileImagesTable.unifiedCode],
                profileImageType = row[AdvertiserProfileImagesTable.profileImageType],
                serviceInfo = row[AdvertiserProfileInfosTable.serviceInfo],
                locationBrief = row[AdvertiserProfileInfosTable.locationBrief],
                introduction = row[AdvertiserProfileInfosTable.introduction]
            )
        }
    }
}
