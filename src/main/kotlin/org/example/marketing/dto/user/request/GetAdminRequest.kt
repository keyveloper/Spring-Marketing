package org.example.marketing.dto.user.request

import jakarta.validation.constraints.NotEmpty

data class GetAdminRequest(
    val targetId: Long
)