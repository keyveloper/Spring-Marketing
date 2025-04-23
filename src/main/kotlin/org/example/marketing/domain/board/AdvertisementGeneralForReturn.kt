package org.example.marketing.domain.board

import org.example.marketing.enums.AdvertisementType

data class AdvertisementGeneralForReturn(
    override val advertisementGeneral: AdvertisementGeneral,
    override val advertisementType: AdvertisementType = AdvertisementType.GENERAL
): Advertisement {
    companion object {
        fun of(
            advertisementGeneral: AdvertisementGeneral,
        ): AdvertisementGeneralForReturn {
            return AdvertisementGeneralForReturn(
                advertisementGeneral = advertisementGeneral,
            )
        }
    }
}