package org.example.marketing.dao.user

import org.example.marketing.dao.BaseDateEntity
import org.example.marketing.dao.BaseDateEntityClass
import org.example.marketing.table.AdminsTable
import org.jetbrains.exposed.dao.id.EntityID

class AdminEntity(id: EntityID<Long>): BaseDateEntity(id, AdminsTable) {
    companion object : BaseDateEntityClass<AdminEntity>(AdminsTable)
    var loginId by AdminsTable.loginId
    var password by AdminsTable.password
}
