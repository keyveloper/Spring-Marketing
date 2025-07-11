package org.example.marketing.table

import org.example.marketing.enums.DraftStatus
import org.jetbrains.exposed.sql.Column
import java.util.UUID

object AdvertisementDraftsTable: BaseDateLongIdTable("advertisement_drafts") {
    val advertiserId: Column<UUID> = uuid("advertiser_id").index()
    val draftStatus: Column<DraftStatus> =
        enumerationByName("draft_status", 255, DraftStatus::class).index()
    val expiredAt: Column<Long> = long("expired_at")
}