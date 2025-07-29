package org.example.marketing.domain.board

import org.example.marketing.dao.board.AdvertisementEntity
import org.example.marketing.enums.AdvertisementLiveStatus
import org.example.marketing.enums.AdvertisementReviewStatus
import org.example.marketing.enums.ChannelType
import org.example.marketing.enums.ReviewType
import java.util.UUID

data class Advertisement(
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
    val updatedAt: Long
) {
    companion object {
        fun of(entity: AdvertisementEntity): Advertisement {
            return Advertisement(
                id = entity.id.value,
                advertiserId = entity.advertiserId,
                title = entity.title,
                reviewType = entity.reviewType,
                channelType = entity.channelType,
                recruitmentNumber = entity.recruitmentNumber,
                itemName = entity.itemName,
                recruitmentStartAt = entity.recruitmentStartAt,
                recruitmentEndAt = entity.recruitmentEndAt,
                announcementAt = entity.announcementAt,
                reviewStartAt = entity.reviewStartAt,
                reviewEndAt = entity.reviewEndAt,
                endAt = entity.endAt,
                liveStatus = entity.liveStatus,
                reviewStatus = entity.reviewStatus,
                siteUrl = entity.siteUrl,
                itemInfo = entity.itemInfo,
                draftId = entity.draftId,
                createdAt = entity.createdAt,
                updatedAt = entity.updatedAt
            )
        }
    }
}
