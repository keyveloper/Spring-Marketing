package org.example.marketing.dto.board.response

import org.example.marketing.dao.board.AdvertisementEntity
import org.example.marketing.enums.ChannelType
import org.example.marketing.enums.DeliveryCategory
import org.example.marketing.enums.ReviewType
import java.util.UUID

data class AdvertisementWithCategoriesV2(
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
        fun of(adEntity: AdvertisementEntity, categories: List<DeliveryCategory>)
        : AdvertisementWithCategoriesV2 {
            return AdvertisementWithCategoriesV2(
                id = adEntity.id.value,
                advertiserId = adEntity.advertiserId,
                title = adEntity.title,
                reviewType = adEntity.reviewType,
                channelType = adEntity.channelType,
                recruitmentNumber = adEntity.recruitmentNumber,
                itemName = adEntity.itemName,
                recruitmentStartAt = adEntity.recruitmentStartAt,
                recruitmentEndAt = adEntity.recruitmentEndAt,
                announcementAt = adEntity.announcementAt,
                reviewStartAt = adEntity.reviewStartAt,
                reviewEndAt = adEntity.reviewEndAt,
                endAt = adEntity.endAt,
                siteUrl = adEntity.siteUrl,
                itemInfo = adEntity.itemInfo,
                createdAt = adEntity.createdAt,
                updatedAt = adEntity.updatedAt,
                categories = categories,
            )
        }
    }
}
