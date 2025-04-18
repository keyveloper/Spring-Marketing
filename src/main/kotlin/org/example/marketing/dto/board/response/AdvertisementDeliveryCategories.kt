package org.example.marketing.dto.board.response

import org.example.marketing.domain.board.AdvertisementDeliveryCategory
import org.example.marketing.enums.DeliveryCategory

data class AdvertisementDeliveryCategories(
    val advertisementId: Long,
    val categories: List<DeliveryCategory>
) {
    companion object {
        fun of(
            domain: List<AdvertisementDeliveryCategory>
        ): AdvertisementDeliveryCategories {
            return AdvertisementDeliveryCategories(
                advertisementId = domain.first().advertisementId,
                categories = domain.map { it.category }
            )
        }
    }
}