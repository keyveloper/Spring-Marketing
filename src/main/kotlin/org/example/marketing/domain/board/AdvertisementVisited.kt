package org.example.marketing.domain.board

import org.example.marketing.dao.board.AdvertisementLocationEntity
import org.example.marketing.enums.AdvertisementType

data class AdvertisementVisited(
    override val advertisementGeneral: AdvertisementGeneral,
    override val advertisementType: AdvertisementType = AdvertisementType.VISITED,
    val city: String?,
    val district: String?,
    val latitude: Double?,
    val longitude: Double?,
    val locationDetails: String,
): Advertisement {
    companion object {
        fun of(
            advertisementGeneral: AdvertisementGeneral,
            location: AdvertisementLocationEntity
        ):AdvertisementVisited {
            return AdvertisementVisited(
                advertisementGeneral = advertisementGeneral,
                city = location.city,
                district = location.district,
                latitude = location.latitude,
                longitude = location.longitude,
                locationDetails = location.detailInfo
            )
        }
    }
}
