package org.example.marketing.dto.user.response

import java.util.*

data class GetInfluencerProfileInfoResult(
    val influencerId: UUID,
    val profilePresignedUrl: String,
    val backgroundPresignedUrl: String,
    val userProfileDraftId: UUID,
    val introduction: String?,
    val job: String?
)
