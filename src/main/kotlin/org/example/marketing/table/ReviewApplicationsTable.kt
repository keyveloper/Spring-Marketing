package org.example.marketing.table

import org.jetbrains.exposed.sql.Column
import java.util.UUID

object ReviewApplicationsTable: BaseDateLongIdTable("review_applications") {
    val influencerId: Column<UUID> = uuid("influencer_id").index()
    val influencerUsername: Column<String> = varchar("influencer_username", 255)
    val influencerEmail: Column<String> = varchar("influencer_email", 255)
    val influencerMobile: Column<String> = varchar("influencer_mobile", 50)
    val advertisementId: Column<Long> = long("advertisement_id").index()
    val applyMemo: Column<String> = varchar("apply_memo", 255)

    init {
        uniqueIndex("uk_advertisement_influencer", advertisementId, influencerId)
    }
}
