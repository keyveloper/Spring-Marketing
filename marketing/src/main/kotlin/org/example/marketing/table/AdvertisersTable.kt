package org.example.marketing.table

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

object AdvertisersTable : LongIdTable("advertiser") {
    val loginId: Column<String> = varchar("login_id", 50)
    val password: Column<String> = varchar("password", 128)
    val email: Column<String> = varchar("email", 100)
    val name: Column<String> = varchar("name", 100)
    val contact: Column<String> = varchar("contact", 50)
    val homepageUrl: Column<String?> = varchar("homepage_url", 255).nullable()
    val advertiserType: Column<Int> = integer("advertiser_type")
    val companyName: Column<String> = varchar("company_name", 50)
}