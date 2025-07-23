package org.example.marketing.dto.timeline.request

import java.util.UUID

data class UploadTimelineAdRequestFromClient(
    val influencerId: UUID,
    val advertisementId: Long
)
