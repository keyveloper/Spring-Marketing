package org.example.marketing.table

import org.example.marketing.enums.AdvertisementStatus
import org.example.marketing.enums.ChannelType
import org.example.marketing.enums.ReviewType
import org.jetbrains.exposed.sql.Column

object AdvertisementsTable: BaseDateLongIdTable("advertisements") {

    val title: Column<String> = varchar("title", 255).index()
    val advertiserId: Column<Long> = long("advertiser_id").index()
    val reviewType: Column<ReviewType> = enumerationByName("review_type", 255, ReviewType::class).index()
    val channelType: Column<ChannelType> = enumerationByName("channel_type", 255, ChannelType::class).index()
    val recruitmentNumber: Column<Int> = integer("recruitment_number").index()
    val itemName: Column<String> = varchar("item_name", 255)
    val recruitmentStartAt: Column<Long> = long("recruitment_start_at").index()
    val recruitmentEndAt: Column<Long> = long("recruitment_end_at").index()
    val announcementAt: Column<Long> = long("announcement_at")
    val reviewStartAt: Column<Long> = long("review_start_at")
    val reviewEndAt: Column<Long> = long("review_end_at")
    val endAt: Column<Long> = long("end_at")
    val status: Column<AdvertisementStatus> =
        enumerationByName("status", 255, AdvertisementStatus::class).index()
    val siteUrl: Column<String?> = text("site_url").nullable()
    val itemInfo: Column<String?> = varchar("item_info", 255).nullable()
    val draftId: Column<Long?> = long("draft_id").nullable().index()
}