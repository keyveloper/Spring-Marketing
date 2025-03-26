package org.example.marketing.table

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

abstract class BaseLongIdTable(tableName: String, idName: String = "id") : LongIdTable(tableName, idName) {
    val createdAt: Column<Long> = long("created_at").clientDefault { System.currentTimeMillis() / 1000 } // second
    val updatedAt: Column<Long> = long("updated_at").clientDefault { System.currentTimeMillis() / 1000 }

}