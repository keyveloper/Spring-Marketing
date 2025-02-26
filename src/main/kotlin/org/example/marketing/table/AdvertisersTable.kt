package org.example.marketing.table

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

object AdvertisersTable: BaseLongIdTable("advertisers") {
    val loginId: Column<String> = varchar("login_id", 255)
    val password: Column<String> = varchar("password", 255)
    val email: Column<String> = varchar("email", 255)
    val name: Column<String> = varchar("name", 255)
    val mobile: Column<String> = varchar("contact", 255)
    val homepageUrl: Column<String?> = text("homepage_url").nullable() // text ?
    val advertiserType: Column<Int> = integer("advertiser_type")
    val companyName: Column<String> = varchar("company_name", 255)
}