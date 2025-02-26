package org.example.marketing.dto.board.request

data class UpdateApplicationRequest (
    val targetId: Long,

    val word: String,
)