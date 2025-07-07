package org.example.marketing.dto.board.response

import org.example.marketing.enums.ChannelType
import org.example.marketing.enums.ReviewType

data class ThumbnailAdCard(
    val presignedUrl: String,
    val itemInfo: String?,
    val title: String,
    val recruitmentStartAt: Long,
    val recruitmentEndAt: Long,
    val channelType: ChannelType,
    val reviewType: ReviewType
)
