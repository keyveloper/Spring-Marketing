package org.example.marketing.dto.board.request

import jakarta.validation.constraints.NotEmpty
import org.example.marketing.enums.ChannelType
import org.example.marketing.enums.DeliveryCategory
import org.example.marketing.enums.ReviewType
import java.time.LocalDateTime

data class MakeNewAdvertisementGeneralRequest(
    @field:NotEmpty
    val title: String,
    val reviewType: ReviewType,
    val channelType: ChannelType,
    val recruitmentNumber: Int,

    @field: NotEmpty
    val itemName: String,
    val recruitmentStartAt: Long,
    val recruitmentEndAt: Long,
    val announcementAt: Long,
    val reviewStartAt: Long,
    val reviewEndAt: Long,
    val endAt: Long,
    val siteUrl: String?,
    val itemInfo: String?,

    // ----- draft -------
    val draftId: Long,
)