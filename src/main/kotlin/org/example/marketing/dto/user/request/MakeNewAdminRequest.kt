package org.example.marketing.dto.user.request

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class MakeNewAdminRequest(
    val loginId: String,

    val password: String
)