package org.example.marketing.table

import org.jetbrains.exposed.sql.Column

object AdminsTable: BaseLongIdTable("admins") {
    val loginId: Column<String> = varchar("login_id", 255).uniqueIndex()
    val password: Column<String> = varchar("password", 255)
}