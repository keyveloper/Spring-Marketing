package org.example.marketing.dto.user.request

import java.util.UUID

data class UploadAdvertiserProfileInfoApiRequest(
    val advertiserId: UUID,
    val advertiserName: String,
    val userProfileDraftId: UUID,
    val serviceInfo: String,
    val locationBrief: String,
    val introduction: String?
)
