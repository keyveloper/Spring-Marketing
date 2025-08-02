package org.example.marketing.dto.board.response

import org.example.marketing.domain.myapplication.MyAppliedAdvertisement
import org.example.marketing.enums.ApplicationReviewStatus

data class ThumbnailAdCardWithApplyInfo(
    val appliedDate: Long?,
    val applicationReviewStatus: ApplicationReviewStatus,
    val reviewStartAt: Long,
    val reviewEndAt: Long,
    val thumbnailAdCardLikedInfo: ThumbnailAdCardWithLikedInfo,
) {
    companion object {
        fun of(
            application: MyAppliedAdvertisement,
            thumbnailAdCardLikedInfo: ThumbnailAdCardWithLikedInfo
        ): ThumbnailAdCardWithApplyInfo {
            return ThumbnailAdCardWithApplyInfo(
                appliedDate = application.applicationCreatedAt,
                applicationReviewStatus = application.applicationReviewStatus,
                reviewStartAt = application.reviewStartAt,
                reviewEndAt = application.reviewEndAt,
                thumbnailAdCardLikedInfo = thumbnailAdCardLikedInfo
            )
        }
    }
}