package org.example.marketing.dto.user.request

import jakarta.validation.constraints.NotEmpty

data class GetAdminRequest(
    @field:NotEmpty
    val targetId: Long
)