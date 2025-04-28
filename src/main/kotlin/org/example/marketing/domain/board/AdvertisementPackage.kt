package org.example.marketing.domain.board

import org.example.marketing.dto.board.request.SaveAdvertisementLocation

data class AdvertisementPackage(
    val advertisementGeneral: AdvertisementGeneral?,
    val advertisementDelivery: AdvertisementDelivery?,
    val advertisementLocation: SaveAdvertisementLocation?
) {
    companion object {
        fun generalOf(
            advertisementGeneral: AdvertisementGeneral
        ): AdvertisementPackage {
            return AdvertisementPackage(
                advertisementGeneral = advertisementGeneral,
                null,
                null
            )
        }

        fun deliveryOf(
            advertisementDelivery: AdvertisementDelivery
        ): AdvertisementPackage {
            return AdvertisementPackage(
                advertisementGeneral = null,
                advertisementDelivery = advertisementDelivery,
                advertisementLocation = null
            )
        }

        // Must Change!!
        fun locationOf(
        ): AdvertisementPackage {
            return AdvertisementPackage(
                null, null,null
            )
        }
    }
}
