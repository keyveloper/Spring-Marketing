package org.example.marketing.dto.noti.request

import org.example.marketing.enums.NotiToInfluencerType
import java.util.UUID

data class UploadNotiToInfluencerApiRequest(
    val message: String,
    val influencerId: UUID,
    val notiToInfluencerType: NotiToInfluencerType
)
