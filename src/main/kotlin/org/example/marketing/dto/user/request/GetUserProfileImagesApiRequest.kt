package org.example.marketing.dto.user.request

import java.util.UUID

data class GetUserProfileImagesApiRequest(
    val userIds: List<UUID>
)
