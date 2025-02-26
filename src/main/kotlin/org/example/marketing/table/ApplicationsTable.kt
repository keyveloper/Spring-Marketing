package org.example.marketing.table

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object ApplicationsTable: LongIdTable("applications") {
    val influencerId: Column<Long> = long("influencer_id").index()
    val AdvertisementId: Column<Long> = long("advertisement_id").index()
    val word: Column<String> = varchar("word", 50)
    val createdAt: Column<LocalDateTime> = datetime("created_at").index()
    val status: Column<Int> = integer("status") // enum
}