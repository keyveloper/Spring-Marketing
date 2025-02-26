package org.example.marketing.dto.board.request

data class MakeNewApplicationRequest(
    val advertisementId: Long,

    val word: String,
)