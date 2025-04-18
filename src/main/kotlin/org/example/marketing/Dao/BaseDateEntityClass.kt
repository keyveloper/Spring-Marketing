package org.example.marketing.dao

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.table.BaseDateLongIdTable
import org.jetbrains.exposed.dao.EntityChangeType
import org.jetbrains.exposed.dao.EntityHook
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.toEntity

abstract class BaseDateEntityClass<E: BaseDateEntity>(table: BaseDateLongIdTable): LongEntityClass<E>(table) {
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