package org.example.marketing.table

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

object InfluencerChannelsTable: LongIdTable("influencer_channels") {
    val influencerId: Column<Long> = long("influencer_id").index()
    val channelCode: Column<Long> = long("channel_code").index()
    val url: Column<String> = varchar("url", 128)
}