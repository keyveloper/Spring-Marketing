package org.example.marketing.domain.user

import org.example.marketing.dao.user.AdminEntity

data class Admin(
    val id: Long,
    val loginId: String,
    val password: String,
) {
    companion object{
        fun of(
            entity: AdminEntity
        ): Admin {
            return Admin(
                id = entity.id.value,
                loginId = entity.loginId,
                password = entity.password
            )
        }
    }
}