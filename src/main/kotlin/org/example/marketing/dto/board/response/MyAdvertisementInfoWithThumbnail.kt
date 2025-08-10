package org.example.marketing.dto.board.response

import org.example.marketing.domain.board.Advertisement
import org.example.marketing.domain.myadvertisement.OfferedAdvertisementJoinedApplication
import org.example.marketing.enums.AdvertisementLiveStatus
import org.example.marketing.enums.AdvertisementReviewStatus
import org.example.marketing.enums.ChannelType
import org.example.marketing.enums.ReviewType
import java.util.UUID


data class MyAdvertisementInfoWithThumbnail(
    val id: Long,
    val advertiserId: UUID,
    val title: String,
    val reviewType: ReviewType,
    val channelType: ChannelType,
    val recruitmentNumber: Int,
    val itemName: String,
    val recruitmentStartAt: Long,
    val recruitmentEndAt: Long,
    val announcementAt: Long,
    val reviewStartAt: Long,
    val reviewEndAt: Long,
    val endAt: Long,
    val liveStatus: AdvertisementLiveStatus,
    val reviewStatus: AdvertisementReviewStatus,
    val siteUrl: String?,
    val itemInfo: String?,
    val draftId: UUID,
    val createdAt: Long,
    val updatedAt: Long,
    val thumbnailUrl: String?,
) {
    companion object {
        fun of(advertisement: Advertisement, thumbnailUrl: String?): MyAdvertisementInfoWithThumbnail {
            return MyAdvertisementInfoWithThumbnail(
                id = advertisement.id,
                advertiserId = advertisement.advertiserId,
                title = advertisement.title,
                reviewType = advertisement.reviewType,
                channelType = advertisement.channelType,
                recruitmentNumber = advertisement.recruitmentNumber,
                itemName = advertisement.itemName,
                recruitmentStartAt = advertisement.recruitmentStartAt,
                recruitmentEndAt = advertisement.recruitmentEndAt,
                announcementAt = advertisement.announcementAt,
                reviewStartAt = advertisement.reviewStartAt,
                reviewEndAt = advertisement.reviewEndAt,
                endAt = advertisement.endAt,
                liveStatus = advertisement.liveStatus,
                reviewStatus = advertisement.reviewStatus,
                siteUrl = advertisement.siteUrl,
                itemInfo = advertisement.itemInfo,
                draftId = advertisement.draftId,
                createdAt = advertisement.createdAt,
                updatedAt = advertisement.updatedAt,
                thumbnailUrl = thumbnailUrl
            )
        }
    }
}