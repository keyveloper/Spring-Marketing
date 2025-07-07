package org.example.marketing.dao.board

import org.example.marketing.enums.ChannelType
import org.example.marketing.enums.DeliveryCategory
import org.example.marketing.enums.ReviewType
import org.example.marketing.table.*
import org.jetbrains.exposed.sql.ResultRow

data class AdvertisementWithCategoriesEntity(
    val id: Long,
    val advertiserId: String,
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
    val categories: List<DeliveryCategory>,
    // 📌 location info
) {
    companion object {
        fun fromRow(row: ResultRow, categories: List<DeliveryCategory>): AdvertisementWithCategoriesEntity {
            return AdvertisementWithCategoriesEntity(
                id = row[AdvertisementsTable.id].value,
                advertiserId = row[AdvertisementsTable.advertiserId],
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
                categories = categories
            )
        }
    }
}
