package org.example.marketing.dto.board.response

import org.example.marketing.dao.board.AdvertisementEntity
import org.example.marketing.domain.board.Advertisement
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
        fun of(advertisement: Advertisement, categories: List<DeliveryCategory>)
        : AdvertisementWithCategoriesV2 {
            return AdvertisementWithCategoriesV2(
                id = advertisement.id,
                advertiserId = advertisement.advertiserId,
                title = advertisement.title,
                reviewType = advertisement.reviewType,
                channelType = advertisement.channelType,
                recruitmentNumber = advertisement.recruitmentNumber,
                itemName = advertisement.itemName,
                recruitmentStartAt = advertisement.recruitmentStartAt,
                recruitmentEndAt = advertisement.recruitmentEndAt,
                announcementAt = advertisement.announcementAt,
                reviewStartAt = advertisement.reviewStartAt,
                reviewEndAt = advertisement.reviewEndAt,
                endAt = advertisement.endAt,
                siteUrl = advertisement.siteUrl,
                itemInfo = advertisement.itemInfo,
                createdAt = advertisement.createdAt,
                updatedAt = advertisement.updatedAt,
                categories = categories,
            )
        }
    }
}
