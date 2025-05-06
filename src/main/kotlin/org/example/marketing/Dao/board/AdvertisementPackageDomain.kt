package org.example.marketing.dao.board

import org.example.marketing.enums.ChannelType
import org.example.marketing.enums.DeliveryCategory
import org.example.marketing.enums.ReviewType
import org.example.marketing.table.AdvertisementDeliveryCategoriesTable
import org.example.marketing.table.AdvertisementImagesTable
import org.example.marketing.table.AdvertisementsTable
import org.jetbrains.exposed.sql.ResultRow

data class AdvertisementPackageDomain(
    val id: Long,
    val title: String,
    val reviewType: ReviewType,
    val channelType: ChannelType,
    val recruitmentNumber: Int,
    val itemName: String,
    val recruitmentStartAt: Long,
    val recruitmentEndAt: Long,
    val announcementAt: Long,
    val reviewStartAt: Long,
    val reviewEndAt: Long,
    val endAt: Long,
    val siteUrl: String?,
    val itemInfo: String?,
    val createdAt: Long,
    val updatedAt: Long,
    val imageUri: String,
    val isThumbnail: Boolean,
    val category: DeliveryCategory?,
    // 📌 location info
) {
    companion object {
        fun fromRow(row: ResultRow): AdvertisementPackageDomain {
            return AdvertisementPackageDomain(
                id = row[AdvertisementsTable.id].value,
                title = row[AdvertisementsTable.title],
                reviewType = row[AdvertisementsTable.reviewType],
                channelType = row[AdvertisementsTable.channelType],
                recruitmentNumber = row[AdvertisementsTable.recruitmentNumber],
                itemName = row[AdvertisementsTable.itemName],
                recruitmentStartAt = row[AdvertisementsTable.recruitmentStartAt],
                recruitmentEndAt = row[AdvertisementsTable.recruitmentEndAt],
                announcementAt = row[AdvertisementsTable.announcementAt],
                reviewStartAt = row[AdvertisementsTable.reviewStartAt],
                reviewEndAt = row[AdvertisementsTable.reviewEndAt],
                endAt = row[AdvertisementsTable.endAt],
                siteUrl = row[AdvertisementsTable.siteUrl],
                itemInfo = row[AdvertisementsTable.itemInfo],
                createdAt = row[AdvertisementsTable.createdAt],
                updatedAt = row[AdvertisementsTable.updatedAt],
                imageUri = row[AdvertisementImagesTable.apiCallUri],
                isThumbnail = row[AdvertisementImagesTable.isThumbnail],
                category = row[AdvertisementDeliveryCategoriesTable.category]
            )
        }
    }
}
