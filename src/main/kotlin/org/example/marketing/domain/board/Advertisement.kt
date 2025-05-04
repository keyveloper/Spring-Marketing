package org.example.marketing.domain.board

import org.example.marketing.enums.AdvertisementType

interface Advertisement {
    val advertisementGeneralFields: AdvertisementGeneralFields
    val advertisementType: AdvertisementType
}