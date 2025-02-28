package org.example.marketing.dao.user

import org.example.marketing.Dao.BaseEntity
import org.example.marketing.Dao.BaseEntityClass
import org.example.marketing.dao.BaseEntity
import org.example.marketing.dao.BaseEntityClass
import org.example.marketing.table.AdminsTable
import org.jetbrains.exposed.dao.id.EntityID

class AdminEntity(id: EntityID<Long>): BaseEntity(id, AdminsTable) {
    companion object : BaseEntityClass<AdminEntity>(AdminsTable)
    var loginId by AdminsTable.loginId
    var password by AdminsTable.password
}
