package org.example.marketing.dto.noti.response

import org.example.marketing.enums.NotiToInfluencerType
import java.time.LocalDateTime
import java.util.UUID

data class NotiToInfluencerInfo(
    val notiId: UUID,
    val message: String,
    val influencerId: UUID,
    val notiToInfluencerType: NotiToInfluencerType,
    val isRead: Boolean,
    val createdAt: LocalDateTime
)
