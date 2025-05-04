package org.example.marketing.domain.board

import org.example.marketing.enums.AdvertisementType

data class AdvertisementGeneralForReturn(
    override val advertisementGeneralFields: AdvertisementGeneralFields,
    override val advertisementType: AdvertisementType = AdvertisementType.GENERAL
): Advertisement {
    companion object {
        fun of(
            advertisementGeneralFields: AdvertisementGeneralFields,
        ): AdvertisementGeneralForReturn {
            return AdvertisementGeneralForReturn(
                advertisementGeneralFields = advertisementGeneralFields,
            )
        }
    }
}