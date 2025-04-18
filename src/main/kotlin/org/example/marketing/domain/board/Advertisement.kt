package org.example.marketing.domain.board

import org.example.marketing.config.CustomDateTimeFormatter
import org.example.marketing.dao.board.AdvertisementEntity
import org.example.marketing.dao.board.AdvertisementLocationEntity
import org.example.marketing.dto.board.response.AdvertisementDeliveryCategories
import org.example.marketing.enums.ChannelType
import org.example.marketing.enums.DeliveryCategory
import org.example.marketing.enums.ReviewType

sealed class Advertisement {

    data class AdvertisementGeneral(
        val id: Long,
        val title: String,
        val reviewType: ReviewType,
        val channelType: ChannelType,
        val recruitmentNumber: Int,
        val itemName: String,
        val recruitmentStartAt: String,
        val recruitmentEndAt: String,
        val announcementAt: String,
        val reviewStartAt: String,
        val reviewEndAt: String,
        val endAt: String,
        val siteUrl: String?,
        val itemInfo: String?,
        val createdAt: String,
        val updatedAt: String,
    ) : Advertisement() {
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
                    recruitmentStartAt = CustomDateTimeFormatter.epochToString(entity.recruitmentStartAt),
                    recruitmentEndAt = CustomDateTimeFormatter.epochToString(entity.recruitmentEndAt),
                    announcementAt = CustomDateTimeFormatter.epochToString(entity.announcementAt),
                    reviewStartAt = CustomDateTimeFormatter.epochToString(entity.reviewStartAt),
                    reviewEndAt = CustomDateTimeFormatter.epochToString(entity.reviewEndAt),
                    endAt = CustomDateTimeFormatter.epochToString(entity.endAt),
                    siteUrl = entity.siteUrl,
                    itemInfo = entity.itemInfo,
                    createdAt = CustomDateTimeFormatter.epochToString(entity.createdAt),
                    updatedAt = CustomDateTimeFormatter.epochToString(entity.updatedAt),
                )
            }
        }
    }

    data class AdvertisementDelivery(
        val id: Long,
        val title: String,
        val reviewType: ReviewType,
        val channelType: ChannelType,
        val recruitmentNumber: Int,
        val itemName: String,
        val recruitmentStartAt: String,
        val recruitmentEndAt: String,
        val announcementAt: String,
        val reviewStartAt: String,
        val reviewEndAt: String,
        val endAt: String,
        val siteUrl: String?,
        val itemInfo: String?,
        val createdAt: String,
        val updatedAt: String,
        val categories: List<DeliveryCategory>
    ) : Advertisement() {
        companion object {
            fun of(
                entity: AdvertisementEntity,
                category: AdvertisementDeliveryCategories
            ): AdvertisementDelivery {
                return AdvertisementDelivery(
                    id = entity.id.value,
                    title = entity.title,
                    reviewType = entity.reviewType,
                    channelType = entity.channelType,
                    recruitmentNumber = entity.recruitmentNumber,
                    itemName = entity.itemName,
                    recruitmentStartAt = CustomDateTimeFormatter.epochToString(entity.recruitmentStartAt),
                    recruitmentEndAt = CustomDateTimeFormatter.epochToString(entity.recruitmentEndAt),
                    announcementAt = CustomDateTimeFormatter.epochToString(entity.announcementAt),
                    reviewStartAt = CustomDateTimeFormatter.epochToString(entity.reviewStartAt),
                    reviewEndAt = CustomDateTimeFormatter.epochToString(entity.reviewEndAt),
                    endAt = CustomDateTimeFormatter.epochToString(entity.endAt),
                    siteUrl = entity.siteUrl,
                    itemInfo = entity.itemInfo,
                    createdAt = CustomDateTimeFormatter.epochToString(entity.createdAt),
                    updatedAt = CustomDateTimeFormatter.epochToString(entity.updatedAt),
                    categories = category.categories
                )
            }
        }
    }


    data class AdvertisementWithLocation(
        val locationEntityId: Long,
        val advertisementId: Long,
        val title: String,
        val reviewType: ReviewType,
        val channelType: ChannelType,
        val recruitmentNumber: Int,
        val itemName: String,
        val recruitmentStartAt: String,
        val recruitmentEndAt: String,
        val announcementAt: String,
        val reviewStartAt: String,
        val reviewEndAt: String,
        val endAt: String,
        val siteUrl: String?,
        val itemInfo: String?,
        val city: String?,
        val district: String?,
        val latitude: Double?,
        val longitude: Double?,
        val locationDetails: String,
        val createdAt: String,
        val updatedAt: String
    ) : Advertisement() {
        companion object {
            fun of(
                advertisement: AdvertisementEntity,
                location: AdvertisementLocationEntity
            ): AdvertisementWithLocation {
                return Advertisement.AdvertisementWithLocation(
                    locationEntityId = location.id.value,
                    advertisementId = advertisement.id.value,
                    title = advertisement.title,
                    reviewType = advertisement.reviewType,
                    channelType = advertisement.channelType,
                    recruitmentNumber = advertisement.recruitmentNumber,
                    itemName = advertisement.itemName,
                    itemInfo = advertisement.itemInfo,
                    recruitmentStartAt = CustomDateTimeFormatter.epochToString(advertisement.recruitmentStartAt),
                    recruitmentEndAt = CustomDateTimeFormatter.epochToString(advertisement.recruitmentEndAt),
                    announcementAt = CustomDateTimeFormatter.epochToString(advertisement.announcementAt),
                    reviewStartAt = CustomDateTimeFormatter.epochToString(advertisement.reviewStartAt),
                    reviewEndAt = CustomDateTimeFormatter.epochToString(advertisement.reviewEndAt),
                    endAt = CustomDateTimeFormatter.epochToString(advertisement.endAt),
                    siteUrl = advertisement.siteUrl,
                    city = location.city,
                    district = location.district,
                    longitude = location.longitude,
                    latitude = location.latitude,
                    locationDetails = location.detailInfo,
                    createdAt = CustomDateTimeFormatter.epochToString(advertisement.createdAt),
                    updatedAt = CustomDateTimeFormatter.epochToString(advertisement.updatedAt)
                )
            }
        }
    }
}