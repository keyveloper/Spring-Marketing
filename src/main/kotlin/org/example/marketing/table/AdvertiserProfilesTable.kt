package org.example.marketing.table

import org.jetbrains.exposed.sql.Column

object AdvertiserProfilesTable: BaseDateLongIdTable("advertiser_profiles") {
    val advertiserId: Column<Long> = long("advertiser_id").uniqueIndex()
    val companyInfo: Column<String?> = varchar("company_info", 255).nullable()
    val companyLocation: Column<String?> = varchar("company_location", 255).nullable()
    val introduction: Column<String?> = varchar("introduction", 255).nullable()
}