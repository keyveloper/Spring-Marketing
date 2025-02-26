package org.example.marketing.Dao.board

import java.time.LocalDateTime

data class Application(
    val id: Long,
    val influencerId: Long,
    val advertisementId: Long,
    var word: String,
    val createdAt: LocalDateTime,
    val status: Int
)