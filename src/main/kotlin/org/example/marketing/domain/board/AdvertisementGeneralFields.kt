package org.example.marketing.domain.board

import org.example.marketing.dao.board.AdvertisementWithCategoriesEntity
import org.example.marketing.enums.ChannelType
import org.example.marketing.enums.ReviewType

data class AdvertisementGeneralFields(
    val id: Long,
    val title: String,
    val advertiserId: String,
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
    val thumbnailUri: String? = null,
) {
    companion object {
        fun of(
            domain: AdvertisementWithCategoriesEntity,
        ): AdvertisementGeneralFields {
            return AdvertisementGeneralFields(
                id = domain.id,
                title = domain.title,
                advertiserId = domain.advertiserId,
                reviewType = domain.reviewType,
                channelType = domain.channelType,
                recruitmentNumber = domain.recruitmentNumber,
                itemName = domain.itemName,
                recruitmentStartAt = domain.recruitmentStartAt,
                recruitmentEndAt = domain.recruitmentEndAt,
                announcementAt = domain.announcementAt,
                reviewStartAt = domain.reviewStartAt,
                reviewEndAt = domain.reviewEndAt,
                endAt = domain.endAt,
                siteUrl = domain.siteUrl,
                itemInfo = domain.itemInfo,
                createdAt = domain.createdAt,
                updatedAt = domain.updatedAt,
            )
        }

    }
}