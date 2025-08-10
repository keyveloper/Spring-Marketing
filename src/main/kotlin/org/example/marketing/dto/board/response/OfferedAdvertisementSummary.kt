package org.example.marketing.dto.board.response

import org.example.marketing.domain.myadvertisement.OfferedAdvertisementJoinedApplication
import org.example.marketing.enums.AdvertisementReviewStatus
import org.example.marketing.enums.ChannelType
import org.example.marketing.enums.ReviewType
import java.util.UUID

data class OfferedAdvertisementSummary(
    val advertisementId: Long,
    val advertiserId: UUID,
    val title: String,
    val reviewType: ReviewType,
    val channelType: ChannelType,
    val itemName: String,
    val recruitmentStartAt: Long,
    val recruitmentEndAt: Long,
    val reviewStartAt: Long,
    val reviewEndAt: Long,
    val advertisementReviewStatus: AdvertisementReviewStatus,
    val thumbnailUrl: String
) {
    companion object {
        fun fromOffered(
            offeredAdvertisementJoinedApplication: OfferedAdvertisementJoinedApplication,
            thumbnailUrl: String,
        ): OfferedAdvertisementSummary {
            return OfferedAdvertisementSummary(
                offeredAdvertisementJoinedApplication.advertisementId,
                offeredAdvertisementJoinedApplication.advertiserId,
                offeredAdvertisementJoinedApplication.title,
                offeredAdvertisementJoinedApplication.reviewType,
                offeredAdvertisementJoinedApplication.channelType,
                offeredAdvertisementJoinedApplication.itemName,
                offeredAdvertisementJoinedApplication.recruitmentStartAt,
                offeredAdvertisementJoinedApplication.recruitmentEndAt,
                offeredAdvertisementJoinedApplication.reviewStartAt,
                offeredAdvertisementJoinedApplication.reviewEndAt,
                offeredAdvertisementJoinedApplication.advertisementReviewStatus,
                thumbnailUrl
            )
        }
    }
}