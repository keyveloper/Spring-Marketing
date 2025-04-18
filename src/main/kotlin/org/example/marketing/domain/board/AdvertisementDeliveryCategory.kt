package org.example.marketing.domain.board

import org.example.marketing.dao.board.AdvertisementDeliveryCategoryEntity
import org.example.marketing.enums.DeliveryCategory
import org.example.marketing.table.AdvertisementDeliveryCategoriesTable

data class AdvertisementDeliveryCategory(
    val advertisementId: Long,
    val category: DeliveryCategory
) {
    companion object {
        fun of(
            entity: AdvertisementDeliveryCategoryEntity
        ): AdvertisementDeliveryCategory {
            return AdvertisementDeliveryCategory(
                advertisementId = entity.advertisementId,
                category = entity.category
            )
        }
    }
}