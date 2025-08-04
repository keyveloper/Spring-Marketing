package org.example.marketing.dto.user.response

import java.util.UUID

data class AdvertiserProfileInfo(
    val id: Long,
    val advertiserId: UUID,
    val advertiserName: String,
    val userProfileDraftId: UUID,
    val serviceInfo: String,
    val locationBrief: String,
    val introduction: String?,
    val createdAt: Long,
    val updatedAt: Long
)
