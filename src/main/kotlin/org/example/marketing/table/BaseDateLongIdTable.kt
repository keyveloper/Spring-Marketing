package org.example.marketing.table

import kotlinx.datetime.Clock
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

abstract class BaseDateLongIdTable(tableName: String, idName: String = "id") : LongIdTable(tableName, idName) {
    val createdAt: Column<Long> = long("created_at").clientDefault { Clock.System.now().toEpochMilliseconds() } // second
    val updatedAt: Column<Long> = long("updated_at").clientDefault { Clock.System.now().toEpochMilliseconds() }
}