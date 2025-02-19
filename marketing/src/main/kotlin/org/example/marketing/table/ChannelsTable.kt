package org.example.marketing.table

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object ChannelsTable: Table("channels") {
    val code: Column<Int> = integer("code")
    val name: Column<String> = varchar("name", 20)

    override val primaryKey = PrimaryKey(code, name = "PK_CHANNEL_CODE")
}