package org.example.marketing.dto.board.response

import org.example.marketing.enums.AdvertisementLiveStatus
import org.example.marketing.enums.AdvertisementReviewStatus
import org.example.marketing.enums.ChannelType
import org.example.marketing.enums.DeliveryCategory
import org.example.marketing.enums.ReviewType
import org.example.marketing.table.AdvertisementDeliveryCategoriesTable
import org.example.marketing.table.AdvertisementsTable
import org.example.marketing.table.ReviewApplicationsTable
import org.jetbrains.exposed.sql.ResultRow
import java.util.UUID

/**
 * LEFT JOIN 결과의 한 행을 표현하는 DTO
 * - advertisements LEFT JOIN advertisement_delivery_categories LEFT JOIN review_applications
 * - 중복 행이 발생할 수 있음 (1:N 관계)
 */
data class AdvertisementWithCategoriesAndAppliedCount(
    // Advertisement fields
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
    // Delivery category (nullable - LEFT JOIN)
    val category: DeliveryCategory?,
    // Review application ID (nullable - LEFT JOIN, for counting)
    val applicationId: Long?
) {
    companion object {
        fun fromRow(row: ResultRow): AdvertisementWithCategoriesAndAppliedCount {
            return AdvertisementWithCategoriesAndAppliedCount(
                id = row[AdvertisementsTable.id].value,
                advertiserId = row[AdvertisementsTable.advertiserId],
                title = row[AdvertisementsTable.title],
                reviewType = row[AdvertisementsTable.reviewType],
                channelType = row[AdvertisementsTable.channelType],
                recruitmentNumber = row[AdvertisementsTable.recruitmentNumber],
                itemName = row[AdvertisementsTable.itemName],
                recruitmentStartAt = row[AdvertisementsTable.recruitmentStartAt],
                recruitmentEndAt = row[AdvertisementsTable.recruitmentEndAt],
                announcementAt = row[AdvertisementsTable.announcementAt],
                reviewStartAt = row[AdvertisementsTable.reviewStartAt],
                reviewEndAt = row[AdvertisementsTable.reviewEndAt],
                endAt = row[AdvertisementsTable.endAt],
                liveStatus = row[AdvertisementsTable.liveStatus],
                reviewStatus = row[AdvertisementsTable.reviewStatus],
                siteUrl = row[AdvertisementsTable.siteUrl],
                itemInfo = row[AdvertisementsTable.itemInfo],
                draftId = row[AdvertisementsTable.draftId],
                createdAt = row[AdvertisementsTable.createdAt],
                updatedAt = row[AdvertisementsTable.updatedAt],
                category = row.getOrNull(AdvertisementDeliveryCategoriesTable.category),
                applicationId = row.getOrNull(ReviewApplicationsTable.id)?.value
            )
        }
    }
}
