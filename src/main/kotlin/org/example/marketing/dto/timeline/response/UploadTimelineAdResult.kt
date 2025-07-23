package org.example.marketing.dto.timeline.response

import java.time.LocalDateTime
import java.util.UUID

data class UploadTimelineAdResult(
    val influencerId: UUID,
    val advertisementId: Long,
    val createdAt: LocalDateTime
)
