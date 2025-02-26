package org.example.marketing.dto.board.request

import java.time.LocalDateTime

data class MakeNewAdvertisementRequest(

    val title: String,

    val reviewType: Int, // Enum

    val recruitment_number: Int, // 모집인원

    val recruitmentFor: String, // Json으로 시간 데이터 저장,
    // 2025.01.01 ~ 2025.01.03

    val announcementAt: LocalDateTime,

    val reviewFor: String, // Json으로
    // 2025.01.01 ~ 2025.01.03

    val endAt: LocalDateTime,

    val itemName: String,

    val itemNoteUrl: String?,

    val itemDescription: String
)