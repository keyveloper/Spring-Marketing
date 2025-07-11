package org.example.marketing.dto.board.response

import org.example.marketing.dao.board.AdvertisementWithCategoriesEntity
import org.example.marketing.enums.ChannelType
import org.example.marketing.enums.DeliveryCategory
import org.example.marketing.enums.ReviewType
import java.util.UUID

data class AdvertisementWithCategories(
    val id: Long,
    val advertiserId: UUID,
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
) {
    companion object {
        fun of(entity: AdvertisementWithCategoriesEntity): AdvertisementWithCategories {
            return AdvertisementWithCategories(
                id = entity.id,
                advertiserId = entity.advertiserId,
                title = entity.title,
                reviewType = entity.reviewType,
                channelType = entity.channelType,
                recruitmentNumber = entity.recruitmentNumber,
                itemName = entity.itemName,
                recruitmentStartAt = entity.recruitmentStartAt,
                recruitmentEndAt = entity.recruitmentEndAt,
                announcementAt = entity.announcementAt,
                reviewStartAt = entity.reviewStartAt,
                reviewEndAt = entity.reviewEndAt,
                endAt = entity.endAt,
                siteUrl = entity.siteUrl,
                itemInfo = entity.itemInfo,
                createdAt = entity.createdAt,
                updatedAt = entity.updatedAt,
                categories = entity.categories,
            )
        }
    }
}
