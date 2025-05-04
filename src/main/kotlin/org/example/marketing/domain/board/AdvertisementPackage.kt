package org.example.marketing.domain.board

import org.example.marketing.enums.DeliveryCategory

data class AdvertisementPackage(
    val advertisementGeneralFields: AdvertisementGeneralFields,
    val categories: List<DeliveryCategory>,
    // -> 📌 add location fields -> dti로 만들 것
) {
    companion object {
        fun of(
            advertisementGeneralFields: AdvertisementGeneralFields,
            categories: List<DeliveryCategory>
        ): AdvertisementPackage {
            return AdvertisementPackage(
                advertisementGeneralFields = advertisementGeneralFields,
                categories = categories
            )
        }
    }
}
