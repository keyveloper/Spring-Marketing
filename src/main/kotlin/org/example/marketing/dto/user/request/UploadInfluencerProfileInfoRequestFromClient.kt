package org.example.marketing.dto.user.request

import java.util.UUID

data class UploadInfluencerProfileInfoRequestFromClient(
    val userProfileDraftId: UUID,
    val introduction: String?,
    val job: String?
)


