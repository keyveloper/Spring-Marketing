package org.example.marketing.domain.myapplication

import org.example.marketing.enums.AdvertisementLiveStatus
import org.example.marketing.enums.AdvertisementReviewStatus
import org.example.marketing.enums.ApplicationReviewStatus
import org.example.marketing.enums.ChannelType
import org.example.marketing.enums.ReviewType
import org.example.marketing.table.AdvertisementsTable
import org.example.marketing.table.ReviewApplicationsTable
import org.jetbrains.exposed.sql.ResultRow
import java.util.UUID

data class MyAppliedAdvertisement(
    // Advertisement fields
    val advertisementId: Long,
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
    val applicationUpdatedAt: Long
) {
    companion object {
        fun fromRow(row: ResultRow): MyAppliedAdvertisement {
            return MyAppliedAdvertisement(
                // Advertisement fields
                advertisementId = row[AdvertisementsTable.id].value,
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
                advertisementCreatedAt = row[AdvertisementsTable.createdAt],
                advertisementUpdatedAt = row[AdvertisementsTable.updatedAt],

                // ReviewApplication fields
                applicationId = row[ReviewApplicationsTable.id].value,
                influencerId = row[ReviewApplicationsTable.influencerId],
                influencerUsername = row[ReviewApplicationsTable.influencerUsername],
                influencerEmail = row[ReviewApplicationsTable.influencerEmail],
                influencerMobile = row[ReviewApplicationsTable.influencerMobile],
                applicationReviewStatus = row[ReviewApplicationsTable.reviewStatus],
                applyMemo = row[ReviewApplicationsTable.applyMemo],
                applicationCreatedAt = row[ReviewApplicationsTable.createdAt],
                applicationUpdatedAt = row[ReviewApplicationsTable.updatedAt]
            )
        }
    }
}
