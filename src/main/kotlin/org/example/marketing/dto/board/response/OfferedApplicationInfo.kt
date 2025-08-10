package org.example.marketing.dto.board.response

import org.example.marketing.domain.myadvertisement.OfferedAdvertisementJoinedApplication
import org.example.marketing.enums.ApplicationReviewStatus
import java.util.*


data class OfferedApplicationInfo(

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
) {
    companion object {
        fun fromOffered(
            offered: OfferedAdvertisementJoinedApplication
        ): OfferedApplicationInfo {
            return OfferedApplicationInfo(
                applicationId = offered.applicationId,
                influencerId = offered.influencerId,
                influencerUsername = offered.influencerUsername,
                influencerEmail = offered.influencerEmail,
                influencerMobile = offered.influencerMobile,
                applicationReviewStatus = offered.applicationReviewStatus,
                applyMemo = offered.applyMemo,
                applicationCreatedAt = offered.applicationCreatedAt,
                applicationUpdatedAt = offered.applicationUpdatedAt
            )
        }
    }
}
