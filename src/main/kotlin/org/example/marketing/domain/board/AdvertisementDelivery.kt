package org.example.marketing.domain.board

import org.example.marketing.enums.AdvertisementType
import org.example.marketing.enums.DeliveryCategory

data class AdvertisementDelivery(
    override val advertisementGeneral: AdvertisementGeneral,
    override val advertisementType: AdvertisementType = AdvertisementType.DELIVERY,
    val categories: List<DeliveryCategory>
): Advertisement {
    companion object {
        fun of(
            advertisementGeneral: AdvertisementGeneral,
            categories: List<DeliveryCategory>
        ): AdvertisementDelivery {
            return AdvertisementDelivery(
                advertisementGeneral = advertisementGeneral,
                categories = categories
            )
        }
    }
}
