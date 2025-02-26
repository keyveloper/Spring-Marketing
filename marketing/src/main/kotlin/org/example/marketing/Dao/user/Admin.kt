package org.example.marketing.Dao.user

import org.example.marketing.Dao.BaseEntity
import org.example.marketing.Dao.BaseEntityClass
import org.example.marketing.table.AdminTable
import org.jetbrains.exposed.dao.id.EntityID

class Admin(id: EntityID<Long>): BaseEntity(id, AdminTable) {
    companion object : BaseEntityClass<Admin>(AdminTable)
    var loginId by AdminTable.loginId
    var password by AdminTable.password
}
