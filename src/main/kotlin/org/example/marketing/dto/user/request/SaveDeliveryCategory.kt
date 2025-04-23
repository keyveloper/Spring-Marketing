package org.example.marketing.dto.user.request

import org.example.marketing.enums.DeliveryCategory

data class SaveDeliveryCategory(
    val advertisementId: Long,
    val categories: List<DeliveryCategory>
) {
    companion object{
        fun of(
            advertisementId: Long,
            categories: List<DeliveryCategory>
        ): SaveDeliveryCategory{
            return SaveDeliveryCategory(
                advertisementId,
                categories
            )
        }
    }
}
