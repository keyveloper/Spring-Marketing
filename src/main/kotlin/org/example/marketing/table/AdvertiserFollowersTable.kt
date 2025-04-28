package org.example.marketing.table

import org.example.marketing.enums.FollowStatus
import org.jetbrains.exposed.sql.Column

object AdvertiserFollowersTable: BaseDateLongIdTable("advertiser_followers") {
    val advertiserId: Column<Long> = long("advertiser_id").index()
    val influencerId: Column<Long> = long("influencer_id").index()
    val followStatus: Column<FollowStatus> =
        enumerationByName("follow_status", 255,  FollowStatus::class).index()

    init {
        uniqueIndex("uk_advertiser_influencer", advertiserId, influencerId)
    }
}
