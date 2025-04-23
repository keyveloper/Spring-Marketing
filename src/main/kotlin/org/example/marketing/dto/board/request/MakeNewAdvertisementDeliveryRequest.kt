package org.example.marketing.dto.board.request

import org.example.marketing.enums.DeliveryCategory

data class MakeNewAdvertisementDeliveryRequest(
    val advertisementGeneralRequest: MakeNewAdvertisementGeneralRequest,
    val categories: List<DeliveryCategory>
)