package org.example.marketing.domain.board

import org.example.marketing.config.CustomDateTimeFormatter
import org.example.marketing.dao.board.AdvertisementEntity
import org.example.marketing.dao.board.AdvertisementLocationEntity
import org.example.marketing.dto.board.response.AdvertisementDeliveryCategories
import org.example.marketing.enums.ChannelType
import org.example.marketing.enums.DeliveryCategory
import org.example.marketing.enums.ReviewType

data class AdvertisementGeneral(
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
) {
    companion object {
        fun of(
            entity: AdvertisementEntity,
        ): AdvertisementGeneral {
            return AdvertisementGeneral(
                id = entity.id.value,
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
                updatedAt = entity.updatedAt
            )
        }
    }
}