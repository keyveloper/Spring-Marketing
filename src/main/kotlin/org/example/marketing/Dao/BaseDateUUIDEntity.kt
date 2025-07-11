package org.example.marketing.dao

import org.example.marketing.table.BaseDateUUIDTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.id.EntityID
import java.util.UUID

abstract class BaseDateUUIDEntity(id: EntityID<UUID>, table: BaseDateUUIDTable) : UUIDEntity(id) {
    val createdAt by table.createdAt
    var updatedAt by table.updatedAt
}