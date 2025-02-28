package org.example.marketing.dto.user.request

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class MakeNewAdminRequest(
    @field: NotEmpty
    @field: NotNull
    val loginId: String,

    val password: String
)