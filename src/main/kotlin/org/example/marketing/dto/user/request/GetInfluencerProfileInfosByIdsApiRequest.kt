package org.example.marketing.dto.user.request

import java.util.UUID

data class GetInfluencerProfileInfosByIdsApiRequest(
    val influencerIds: List<UUID>
)
