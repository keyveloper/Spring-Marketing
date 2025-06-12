package org.example.marketing.dao.functions

import org.example.marketing.dao.board.AdvertisementPackageEntity
import org.example.marketing.enums.AdvertiserType
import org.example.marketing.table.AdvertiserProfileImagesTable
import org.example.marketing.table.AdvertisersTable
import org.jetbrains.exposed.sql.ResultRow

data class AdvertisementPackageWithAdvertiserEntity(
    // 📦 advertisement package
    val advertisementPackageEntity: AdvertisementPackageEntity,

    // 😏 user info
    val companyName: String,
    val advertiserType: AdvertiserType,
    val email: String,
    val contact: String,

    // 🖼️ profile
    val advertiserProfileUnifiedCode: String?,
) {
    companion object {
        fun fromRowWithPkgDomain(
            pkgDomain: AdvertisementPackageEntity,
            row: ResultRow
        ): AdvertisementPackageWithAdvertiserEntity {
            return AdvertisementPackageWithAdvertiserEntity(
                advertisementPackageEntity = pkgDomain,
                companyName = row[AdvertisersTable.loginId],
                advertiserType = row[AdvertisersTable.advertiserType],
                email = row[AdvertisersTable.email],
                contact = row[AdvertisersTable.contact],
                advertiserProfileUnifiedCode = row[AdvertiserProfileImagesTable.unifiedCode]
            )
        }
    }
}