package org.example.marketing.dto.noti.request

import org.example.marketing.enums.NotiToInfluencerType
import java.util.UUID

data class UploadNotiToInfluencerRequestFromClient(
    val message: String,
    val influencerId: UUID,
    val notiToInfluencerType: NotiToInfluencerType
)
