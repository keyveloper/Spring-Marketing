package org.example.marketing.dto.timeline.request

import org.example.marketing.enums.TimelineCursor
import java.util.UUID

data class GetTimelineAdsApiRequest(
    val influencerId: UUID,
    val cursor: TimelineCursor,
    val pivotTime: Long?
)
