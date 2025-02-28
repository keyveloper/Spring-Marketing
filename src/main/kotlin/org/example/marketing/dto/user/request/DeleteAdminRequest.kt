package org.example.marketing.dto.user.request

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class DeleteAdminRequest(
    @field: NotNull
    @field: NotEmpty
    val targetId: Long
)