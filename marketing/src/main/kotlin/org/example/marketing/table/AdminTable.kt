package org.example.marketing.table

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

object AdminTable: BaseLongIdTable("admins") {
    val loginId: Column<String> = varchar("login_id", 255)
    val password: Column<String> = varchar("password", 255)
}