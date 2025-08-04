package org.example.marketing.dto.user.request

import java.util.UUID

data class GetAdvertiserProfileInfosByIdsApiRequest(
    val advertiserIds: List<UUID>
)
