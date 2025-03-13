package org.example.marketing.domain.board

import org.example.marketing.dao.board.AdvertisementLocationEntity
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


data class AdvertisementLocation(
    val id: Long,
    val advertisementId: Long,
    val city: String?,
    val district: String?,
    val latitude: Double?,
    val longitude: Double?,
    val createdAt: String,
    val updatedAt: String
) {
    companion object {
        fun of(entity: AdvertisementLocationEntity): AdvertisementLocation {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            return AdvertisementLocation(
                id = entity.id.value,
                advertisementId = entity.advertisementId,
                city = entity.city,
                district = entity.district,
                latitude = entity.latitude,
                longitude = entity.longitude,
                createdAt = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(entity.createdAt),
                    ZoneId.systemDefault()
                ).format(formatter),
                updatedAt = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(entity.updatedAt),
                    ZoneId.systemDefault()
                ).format(formatter)
            )
        }
    }
}
