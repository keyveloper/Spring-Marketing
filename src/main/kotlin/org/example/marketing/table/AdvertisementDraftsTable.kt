package org.example.marketing.table

import org.example.marketing.enums.DraftStatus
import org.jetbrains.exposed.sql.Column

object AdvertisementDraftsTable: BaseDateLongIdTable("advertisement_drafts") {
    val advertiserId: Column<Long> = long("advertiser_id").index()
    val draftStatus: Column<DraftStatus> =
        enumerationByName("draft_status", 255, DraftStatus::class).index()
    val expiredAt: Column<Long> = long("expired_at")
}