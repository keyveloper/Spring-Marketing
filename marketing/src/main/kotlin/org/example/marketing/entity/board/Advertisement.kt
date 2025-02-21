package org.example.marketing.entity.board

import java.time.LocalDateTime

data class Advertisement(
    val id: Long,
    val title: String,
    val reviewType: Int,
    val recruitmentNumber: Int,
    val createdAt: LocalDateTime,
    val recruitmentFor: String,
    val announcementAt: LocalDateTime,
    val reviewFor: String,
    val endAt: LocalDateTime,
    val status: Int,
    val itemName: String,
    val itemNoteUrl: String?,
    val itemDescription: String
)