package org.example.marketing.dto.noti.response

import org.example.marketing.enums.NotiToAdvertiserType
import java.time.LocalDateTime
import java.util.UUID

data class NotiToAdvertiserInfo(
    val notiId: UUID,
    val message: String,
    val advertiserId: UUID,
    val notiToAdvertiserType: NotiToAdvertiserType,
    val isRead: Boolean,
    val createdAt: LocalDateTime
)
