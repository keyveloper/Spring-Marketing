package org.example.marketing.domain.board

import org.example.marketing.config.CustomDateTimeFormatter
import org.example.marketing.dao.board.AdvertisementLocationEntity

data class AdvertisementLocation(
    val id: Long,
    val advertisementId: Long,
    val city: String?,
    val district: String?,
    val latitude: Double?,
    val longitude: Double?,
    val detailInfo: String,
    val createdAt: String,
    val updatedAt: String
) : Advertisement() {
    companion object {
        fun of(entity: AdvertisementLocationEntity): AdvertisementLocation {
            return AdvertisementLocation(
                id = entity.id.value,
                advertisementId = entity.advertisementId,
                city = entity.city,
                district = entity.district,
                latitude = entity.latitude,
                longitude = entity.longitude,
                detailInfo = entity.detailInfo,
                createdAt = CustomDateTimeFormatter.epochToString(entity.createdAt),
                updatedAt = CustomDateTimeFormatter.epochToString(entity.updatedAt)
            )
        }
    }
}
