package org.example.marketing.dto.board.request

import org.example.marketing.enum.ReviewType

data class GetAdvertisementRequestByReviewTypes(
    val types: List<ReviewType>
)
