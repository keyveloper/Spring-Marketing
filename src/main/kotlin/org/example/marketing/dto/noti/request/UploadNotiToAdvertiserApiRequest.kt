package org.example.marketing.dto.noti.request

import org.example.marketing.enums.NotiToAdvertiserType
import java.util.UUID

data class UploadNotiToAdvertiserApiRequest(
    val message: String,
    val advertiserId: UUID,
    val notiToAdvertiserType: NotiToAdvertiserType
)
