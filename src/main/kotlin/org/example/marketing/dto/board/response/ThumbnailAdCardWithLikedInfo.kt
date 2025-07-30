package org.example.marketing.dto.board.response

import org.example.marketing.enums.ChannelType
import org.example.marketing.enums.LikeStatus
import org.example.marketing.enums.ReviewType

data class ThumbnailAdCardWithLikedInfo(
    val advertisementId: Long,
    val presignedUrl: String,
    val itemInfo: String?,
    val title: String,
    val recruitmentStartAt: Long,
    val recruitmentEndAt: Long,
    val recruitNumber: Int,
    val appliedCount: Int,
    val channelType: ChannelType,
    val reviewType: ReviewType,
    val isLiked: LikeStatus
) {
    companion object {
        fun of(
            ad: AdvertisementWithCategoriesAndAppliedCountResult,
            thumbnailUrl: String,
            isLiked: LikeStatus
        ): ThumbnailAdCardWithLikedInfo {
            return ThumbnailAdCardWithLikedInfo(
                advertisementId = ad.id,
                title = ad.title,
                itemInfo = ad.itemInfo,
                recruitmentStartAt = ad.recruitmentStartAt,
                recruitmentEndAt = ad.recruitmentEndAt,
                recruitNumber = ad.recruitmentNumber,
                appliedCount = ad.appliedCount,
                channelType = ad.channelType,
                reviewType = ad.reviewType,
                presignedUrl = thumbnailUrl,
                isLiked = isLiked
            )
        }
    }
}
