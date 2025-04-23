package org.example.marketing.dto.board.request

import org.example.marketing.enums.DeliveryCategory
import org.example.marketing.enums.TimeLineDirection

data class GetDeliveryAdvertisementsTimelineByCategoryRequest(
    val pivotTime: Long,
    val timeLineDirection: TimeLineDirection,
    val deliverCateGory: DeliveryCategory
) {

}