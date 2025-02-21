package org.example.marketing.dto.board

import java.time.LocalDateTime

data class UpdateAdvertisementRequest (
    val advertiserId: Long,

    val title: String,

    val reviewType: Int, // enum

    val recruitment_number: Int, // 모집인원

    val recruitmentFor: String, // Json으로 시간 데이터 저장,
    // 2025.01.01 ~ 2025.01.03

    val announcementAt: LocalDateTime,

    val reviewFor: String, // Json으로
    // 2025.01.01 ~ 2025.01.03

    val endAt: LocalDateTime,

    val itemName: String,

    val itemNoteUrl: String,

    val itemDescription: String
)