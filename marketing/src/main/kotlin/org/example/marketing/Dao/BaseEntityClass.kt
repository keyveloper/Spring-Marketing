package org.example.marketing.Dao

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketing.table.BaseLongIdTable
import org.jetbrains.exposed.dao.EntityChangeType
import org.jetbrains.exposed.dao.EntityHook
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.toEntity
import java.time.LocalDateTime

abstract class BaseEntityClass<E: BaseEntity>(table: BaseLongIdTable): LongEntityClass<E>(table) {
    private val logger = KotlinLogging.logger {}
    init {
        EntityHook.subscribe { action ->
            if (action.changeType == EntityChangeType.Updated) {
                try {
                    action.toEntity(this)?.updatedAt = LocalDateTime.now()
                } catch (e: Exception) {
                    logger.warn { "Failed to update entity $this updatedAt" }
                }
            }
        }
    }
}