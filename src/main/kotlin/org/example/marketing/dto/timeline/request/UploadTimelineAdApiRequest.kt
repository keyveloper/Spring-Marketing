package org.example.marketing.dto.timeline.request

import java.util.UUID

data class UploadTimelineAdApiRequest(
    val influencerId: UUID,
    val advertisementId: Long
)
