package org.example.marketing.dao

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.table.BaseDateUUIDTable
import org.jetbrains.exposed.dao.EntityChangeType
import org.jetbrains.exposed.dao.EntityHook
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.toEntity
import java.util.UUID

abstract class BaseDateUUIDEntityClass<E: BaseDateUUIDEntity>(table: BaseDateUUIDTable): UUIDEntityClass<E>(table) {
    private val logger = KotlinLogging.logger {}
    init {
        EntityHook.subscribe { action ->
            if (action.changeType == EntityChangeType.Updated) {
                try {
                    action.toEntity(this)?.updatedAt = System.currentTimeMillis()
                } catch (e: Exception) {
                    logger.warn { "Failed to update entity $this updatedAt" }
                }
            }
        }
    }
}