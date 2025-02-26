package org.example.marketing.table

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object AdvertisementsTable: LongIdTable("advertisements") {

    val title: Column<String> = varchar("title", 50).index()

    val reviewType: Column<Int> = integer("reviewType").index()

    val recruitmentNumber: Column<Int> = integer("recruitment_number").index()

    val createdAt: Column<LocalDateTime> = datetime("created_at").index()

    val recruitmentFor: Column<String> = varchar("recruitment_for", 255)

    val announcementAt: Column<LocalDateTime> = datetime("announcement_at")

    val reviewFor: Column<String> = varchar("review_for", 255)

    val endAt: Column<LocalDateTime> = datetime("end_at").index()

    val status: Column<Int> = integer("status")

    val itemName: Column<String> = varchar("item_name", 100)

    val itemNoteUrl: Column<String?> = varchar("item_note_url", 255).nullable()

    val itemDescription: Column<String> = text("item_description")
}