package org.example.marketing.dto.board.request

import org.example.marketing.enums.ReviewType

data class GetAdvertisementRequestByReviewTypes(
    val types: List<ReviewType>
)
