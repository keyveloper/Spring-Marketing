package org.example.marketing.dao.board

import java.time.LocalDateTime

data class Advertisement(
    val id: Long,
    var title: String,
    var reviewType: Int,
    var recruitmentNumber: Int,
    var createdAt: LocalDateTime,
    var recruitmentFor: String,
    var announcementAt: LocalDateTime,
    var reviewFor: String,
    var endAt: LocalDateTime,
    var status: Int,
    var itemName: String,
    var itemNoteUrl: String?,
    var itemDescription: String
)