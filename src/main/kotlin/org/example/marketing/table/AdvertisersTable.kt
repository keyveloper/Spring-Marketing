package org.example.marketing.table

import org.example.marketing.enums.AdvertiserType
import org.example.marketing.enums.UserStatus
import org.jetbrains.exposed.sql.Column

object AdvertisersTable: BaseDateLongIdTable("advertisers") {
    val loginId: Column<String> = varchar("login_id", 255).uniqueIndex()
    val password: Column<String> = varchar("password", 255)
    val status: Column<UserStatus> = enumerationByName("status", 255, UserStatus::class).index()
    val email: Column<String> = varchar("email", 100)
    val contact: Column<String> = varchar("contact", 50)
    val homepageUrl: Column<String?> = varchar("homepage_url", 255).nullable()
    val advertiserType: Column<AdvertiserType> =
        enumerationByName("advertiser_type", 255, AdvertiserType::class)
    val companyName: Column<String> = varchar("company_name", 50)
}