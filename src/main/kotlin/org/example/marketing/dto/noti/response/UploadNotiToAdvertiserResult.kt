package org.example.marketing.dto.noti.response

import java.time.LocalDateTime
import java.util.UUID

data class UploadNotiToAdvertiserResult(
    val notiId: UUID,
    val message: String,
    val createdAt: LocalDateTime
)
