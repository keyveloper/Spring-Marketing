package org.example.marketing.dto.user.request

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class UpdateAdminRequest(
    @field:NotEmpty
    @field:NotNull
    val targetId: Long,

    @field:NotEmpty
    val newLoginId: String?,

    @field:NotEmpty
    val newPassword: String?
)