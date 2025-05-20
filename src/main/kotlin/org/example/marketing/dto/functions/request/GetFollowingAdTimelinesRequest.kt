package org.example.marketing.dto.functions.request

import org.example.marketing.enums.TimeLineDirection

data class GetFollowingAdTimelinesRequest(
    val direction: TimeLineDirection,
    val pivotAt: Long,
)