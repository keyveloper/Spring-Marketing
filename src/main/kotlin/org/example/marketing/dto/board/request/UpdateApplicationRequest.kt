package org.example.marketing.dto.board.request

import jakarta.validation.constraints.NotEmpty
import org.example.marketing.enums.ChannelType
import org.example.marketing.enums.ReviewType
import java.time.LocalDateTime

data class UpdateApplicationRequest (
    @field:NotEmpty
    val title: String?,

    @field:NotEmpty
    val reviewType: ReviewType?,

    @field:NotEmpty
    val channelType: ChannelType?,

    val recruitmentNumber: Int?,

    @field:NotEmpty
    val itemName: String?,

    val recruitmentStartAt: LocalDateTime?,

    val recruitmentEndAt: LocalDateTime?,

    val announcementAt: LocalDateTime?,

    val reviewStartAt: LocalDateTime?,

    val reviewEndAt: LocalDateTime?,

    val endAt: LocalDateTime?,

    val siteUrl: String?,

    val itemInfo: String?,
)