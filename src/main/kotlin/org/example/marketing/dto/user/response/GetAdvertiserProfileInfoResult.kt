package org.example.marketing.dto.user.response

import java.util.*

data class GetAdvertiserProfileInfoResult(
    val id: Long,
    val advertiserId: UUID,
    val userProfileDraftId: UUID,
    val serviceInfo: String,
    val locationBrief: String,
    val introduction: String?,
    val createdAt: Long,
    val updatedAt: Long
)
