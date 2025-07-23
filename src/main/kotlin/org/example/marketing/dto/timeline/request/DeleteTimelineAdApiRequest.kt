package org.example.marketing.dto.timeline.request

import java.util.UUID

data class DeleteTimelineAdApiRequest(
    val influencerId: UUID,
    val advertisementId: Long
)
