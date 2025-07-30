package org.example.marketing.dto.like.request

import java.util.UUID

data class UnLikeRequestFromClient(
    val influencerId: UUID,
    val advertisementId: Long
)
