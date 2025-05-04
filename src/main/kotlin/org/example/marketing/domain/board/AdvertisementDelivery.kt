package org.example.marketing.domain.board

import org.example.marketing.enums.AdvertisementType
import org.example.marketing.enums.DeliveryCategory

data class AdvertisementDelivery(
    override val advertisementGeneralFields: AdvertisementGeneralFields,
    override val advertisementType: AdvertisementType = AdvertisementType.DELIVERY,
    val categories: List<DeliveryCategory>
): Advertisement {
    companion object {
        fun of(
            advertisementGeneralFields: AdvertisementGeneralFields,
            categories: List<DeliveryCategory>
        ): AdvertisementDelivery {
            return AdvertisementDelivery(
                advertisementGeneralFields = advertisementGeneralFields,
                categories = categories
            )
        }
    }
}
