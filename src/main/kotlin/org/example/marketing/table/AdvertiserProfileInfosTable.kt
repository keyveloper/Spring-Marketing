package org.example.marketing.table

import org.jetbrains.exposed.sql.Column

object AdvertiserProfileInfosTable: BaseDateLongIdTable("advertiser_profile_infos") {
    val advertiserId: Column<Long> = long("advertiser_id").uniqueIndex()
    val serviceInfo: Column<String> = varchar("service_info", 255)
    val locationBrief: Column<String> = varchar("location_brief", 255)
    val introduction: Column<String?> = varchar("introduction", 255).nullable()
}