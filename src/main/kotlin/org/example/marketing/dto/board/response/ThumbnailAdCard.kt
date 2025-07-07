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
) {
    companion object {
        fun of(ad: AdvertisementWithCategories, thumbnailUrl: String): ThumbnailAdCard {
            return ThumbnailAdCard(
                presignedUrl = thumbnailUrl,
                itemInfo = ad.itemInfo,
                title = ad.title,
                recruitmentStartAt = ad.recruitmentStartAt,
                recruitmentEndAt = ad.recruitmentEndAt,
                channelType = ad.channelType,
                reviewType = ad.reviewType
            )
        }
    }
}
