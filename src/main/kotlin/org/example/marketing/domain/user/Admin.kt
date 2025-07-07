package org.example.marketing.domain.user

import org.example.marketing.config.CustomDateTimeFormatter
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
            return Admin(
                id = entity.id.value,
                loginId = entity.loginId,
                password = entity.password,
                createdAt = CustomDateTimeFormatter.epochToString(entity.createdAt),
                updatedAt = CustomDateTimeFormatter.epochToString(entity.updatedAt)
            )
        }
    }
}