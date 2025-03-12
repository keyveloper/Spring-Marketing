package org.example.marketing.dto.board.request

import org.example.marketing.enum.ChannelType
import org.example.marketing.enum.ReviewType

data class UpdateAdvertisement(
    val targetId: Long,

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
) {
    companion object {
        fun of(request: UpdateAdvertisementRequest): UpdateAdvertisement{
            return UpdateAdvertisement(
                targetId = request.advertisementId,
                title = request.title,
                reviewType = request.reviewType,
                channelType = request.channelType,
                recruitmentNumber = request.recruitmentNumber,
                itemName = request.itemName,
                recruitmentStartAt = request.recruitmentStartAt,
                recruitmentEndAt = request.recruitmentEndAt,
                announcementAt = request.announcementAt,
                reviewStartAt = request.reviewStartAt,
                reviewEndAt = request.reviewEndAt,
                endAt = request.endAt,
                siteUrl = request.siteUrl,
                itemInfo = request.itemInfo
            )
        }
    }
}
