package org.example.marketing.table

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object InfluencersTable: LongIdTable("influencers") {
    val loginId: Column<String> = varchar("login_id", 50)
    val password: Column<String> = varchar("password", 128)
    val email: Column<String> = varchar("email", 100)
    val name: Column<String> = varchar("name", 100) // user real name
    val contact: Column<String> = varchar("contact", 50)
    val birthday: Column<LocalDateTime?> = datetime("birthday").nullable()
    val createdAt: Column<LocalDateTime?> = datetime("created_at").nullable()
}