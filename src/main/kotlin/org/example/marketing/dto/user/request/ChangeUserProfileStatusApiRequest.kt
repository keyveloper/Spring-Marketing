package org.example.marketing.dto.user.request

import org.example.marketing.enums.UserType
import java.util.UUID

data class ChangeUserProfileStatusApiRequest(
    val entityId: Long,
    val userId: UUID,
    val userType: UserType
)
