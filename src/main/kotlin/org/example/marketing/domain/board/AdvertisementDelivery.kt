package org.example.marketing.domain.board

import org.example.marketing.dto.board.response.AdvertisementDeliveryCategories
import org.example.marketing.enums.AdvertisementType
import org.example.marketing.enums.AdvertiserType
import org.example.marketing.enums.DeliveryCategory

data class AdvertisementDelivery(
    override val advertisementGeneral: AdvertisementGeneral,
    override val advertisementType: AdvertisementType = AdvertisementType.DELIVERY,
    val categories: AdvertisementDeliveryCategories
): Advertisement {
    companion object {
        fun of(
            advertisementGeneral: AdvertisementGeneral,
            categories: AdvertisementDeliveryCategories
        ): AdvertisementDelivery {
            return AdvertisementDelivery(
                advertisementGeneral = advertisementGeneral,
                categories = categories
            )
        }
    }
}
