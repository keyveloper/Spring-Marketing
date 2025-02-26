package org.example.marketing.table

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

object KeywordsTable: LongIdTable("keywords") {
    val advertisementId: Column<Long> = long("advertisement_id").index()

    val keyword: Column<String> = varchar("keyword", 12)
}