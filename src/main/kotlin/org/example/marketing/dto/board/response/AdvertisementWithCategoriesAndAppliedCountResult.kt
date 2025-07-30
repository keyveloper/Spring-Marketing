package org.example.marketing.dto.board.response

import org.example.marketing.enums.AdvertisementLiveStatus
import org.example.marketing.enums.AdvertisementReviewStatus
import org.example.marketing.enums.ChannelType
import org.example.marketing.enums.DeliveryCategory
import org.example.marketing.enums.ReviewType
import java.util.UUID

/**
 * JOIN 결과를 advertisementId 기준으로 그룹핑한 최종 결과 DTO
 * - 중복 행 제거됨
 * - categories: 해당 광고의 모든 카테고리 (중복 제거)
 * - appliedCount: 해당 광고의 지원자 수
 */
data class AdvertisementWithCategoriesAndAppliedCountResult(
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
    val categories: List<DeliveryCategory>,
    val appliedCount: Int
) {
    companion object {
        /**
         * 중복 행 리스트를 advertisementId 기준으로 그룹핑하여 단일 결과로 변환
         */
        fun fromRows(rows: List<AdvertisementWithCategoriesAndAppliedCount>): List<AdvertisementWithCategoriesAndAppliedCountResult> {
            return rows
                .groupBy { it.id }
                .map { (_, groupedRows) ->
                    val first = groupedRows.first()
                    val categories = groupedRows
                        .mapNotNull { it.category }
                        .distinct()
                    val appliedCount = groupedRows
                        .mapNotNull { it.applicationId }
                        .distinct()
                        .count()

                    AdvertisementWithCategoriesAndAppliedCountResult(
                        id = first.id,
                        advertiserId = first.advertiserId,
                        title = first.title,
                        reviewType = first.reviewType,
                        channelType = first.channelType,
                        recruitmentNumber = first.recruitmentNumber,
                        itemName = first.itemName,
                        recruitmentStartAt = first.recruitmentStartAt,
                        recruitmentEndAt = first.recruitmentEndAt,
                        announcementAt = first.announcementAt,
                        reviewStartAt = first.reviewStartAt,
                        reviewEndAt = first.reviewEndAt,
                        endAt = first.endAt,
                        liveStatus = first.liveStatus,
                        reviewStatus = first.reviewStatus,
                        siteUrl = first.siteUrl,
                        itemInfo = first.itemInfo,
                        draftId = first.draftId,
                        createdAt = first.createdAt,
                        updatedAt = first.updatedAt,
                        categories = categories,
                        appliedCount = appliedCount
                    )
                }
        }
    }
}
