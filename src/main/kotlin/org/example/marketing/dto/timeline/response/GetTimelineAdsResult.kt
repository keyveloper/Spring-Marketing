package org.example.marketing.dto.timeline.response

data class GetTimelineAdsResult(
    val timelineAds: List<TimelineAdInfo>,
    val hasNext: Boolean,
    val nextPivotTime: Long?
)
