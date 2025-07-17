package org.example.marketing.dto.functions.request

import jakarta.validation.constraints.NotEmpty

data class ApplyReviewRequest(
    val advertisementId: Long,

    @field:NotEmpty
    val applyMemo: String
)
