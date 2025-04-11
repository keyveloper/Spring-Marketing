package org.example.marketing.dto.board.request

import org.example.marketing.enums.ChannelType
import org.example.marketing.enums.ReviewType

data class UpdateAdvertisementRequest (
    val advertisementId: Long,

    val title: String?,

    val reviewType: ReviewType?,

    val channelType: ChannelType?,

    val recruitmentNumber: Int?,

    val itemName: String?,

    val recruitmentStartAt: Long?,

    val recruitmentEndAt: Long?,

    val announcementAt: Long?,

    val reviewStartAt: Long?,

    val reviewEndAt: Long?,

    val endAt: Long?,

    val siteUrl: String?,

    val itemInfo: String?,
)