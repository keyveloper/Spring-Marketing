package org.example.marketing.domain.user

import org.example.marketing.dao.user.AdminEntity
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

data class Admin(
    val id: Long,
    val loginId: String,
    val password: String,
    val createdAt: String,
    val updatedAt: String
) {
    companion object{
        fun of(
            entity: AdminEntity
        ): Admin {
            val createdAtEpochTime: Long = entity.createdAt
            val updatedAtEpochTime: Long = entity.updatedAt

            val createdAtInstant = Instant.ofEpochMilli(createdAtEpochTime)
            val updatedAtInstant = Instant.ofEpochMilli(updatedAtEpochTime)

            val createdAtLocalDateTime = LocalDateTime.ofInstant(createdAtInstant, ZoneId.systemDefault())
            val updatedAtLocalDateTime = LocalDateTime.ofInstant(updatedAtInstant, ZoneId.systemDefault())

            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            return Admin(
                id = entity.id.value,
                loginId = entity.loginId,
                password = entity.password,
                createdAt = createdAtLocalDateTime.format(formatter),
                updatedAt = updatedAtLocalDateTime.format(formatter)
            )
        }
    }
}