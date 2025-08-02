package org.example.marketing.dto.user.response

import java.util.UUID

data class GetUserInfoResult(
    val userId: UUID,
    val email: String?,
    val emailVerified: Boolean?,
    val phoneNumber: String?,
    val phoneNumberVerified: Boolean?,
    val name: String?,
    val userStatus: String?,
    val enabled: Boolean?,
    val createdAt: String?,
    val updatedAt: String?
)
