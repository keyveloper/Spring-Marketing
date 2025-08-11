package org.example.marketing.dto.board.response

import org.example.marketing.domain.myadvertisement.OfferedApplication
import org.example.marketing.enums.AdvertisementReviewStatus
import org.example.marketing.enums.ApplicationReviewStatus
import org.example.marketing.enums.ChannelType
import org.example.marketing.enums.ReviewType
import java.util.*


data class OfferedApplicationInfo(
    // Advertisement fields
    val advertisementId: Long,
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
    val advertisementReviewStatus: AdvertisementReviewStatus,
    val siteUrl: String?,
    val itemInfo: String?,
    val draftId: UUID,
    val advertisementCreatedAt: Long,
    val advertisementUpdatedAt: Long,

    // ReviewApplication fields
    val applicationId: Long,
    val influencerId: UUID,
    val influencerUsername: String,
    val influencerEmail: String,
    val influencerMobile: String,
    val applicationReviewStatus: ApplicationReviewStatus,
    val applyMemo: String,
    val applicationCreatedAt: Long,
    val applicationUpdatedAt: Long,

    // Aggregated field
    val appliedCount: Int
) {
    companion object {
        fun of(offeredApplication: OfferedApplication): OfferedApplicationInfo {
            return OfferedApplicationInfo(
                advertisementId = offeredApplication.advertisementId,
                title = offeredApplication.title,
                reviewType = offeredApplication.reviewType,
                channelType = offeredApplication.channelType,
                recruitmentNumber = offeredApplication.recruitmentNumber,
                itemName = offeredApplication.itemName,
                recruitmentStartAt = offeredApplication.recruitmentStartAt,
                recruitmentEndAt = offeredApplication.recruitmentEndAt,
                announcementAt = offeredApplication.announcementAt,
                reviewStartAt = offeredApplication.reviewStartAt,
                reviewEndAt = offeredApplication.reviewEndAt,
                endAt = offeredApplication.endAt,
                advertisementReviewStatus = offeredApplication.advertisementReviewStatus,
                siteUrl = offeredApplication.siteUrl,
                itemInfo = offeredApplication.itemInfo,
                draftId = offeredApplication.draftId,
                advertisementCreatedAt = offeredApplication.advertisementCreatedAt,
                advertisementUpdatedAt = offeredApplication.advertisementUpdatedAt,
                applicationId = offeredApplication.applicationId,
                influencerId = offeredApplication.influencerId,
                influencerUsername = offeredApplication.influencerUsername,
                influencerEmail = offeredApplication.influencerEmail,
                influencerMobile = offeredApplication.influencerMobile,
                applicationReviewStatus = offeredApplication.applicationReviewStatus,
                applyMemo = offeredApplication.applyMemo,
                applicationCreatedAt = offeredApplication.applicationCreatedAt,
                applicationUpdatedAt = offeredApplication.applicationUpdatedAt,
                appliedCount = offeredApplication.appliedCount
            )
        }
    }
}
