package org.example.marketing.domain.user

import org.example.marketing.dao.user.AdminEntity
import org.example.marketing.enum.UserType

data class AdminPrincipal(
    val adminId: Long,
    val loginId: String,
    val userType: UserType = UserType.ADMIN
) {
    companion object {
        fun of(admin: AdminEntity): AdminPrincipal {
            return AdminPrincipal(
                adminId = admin.id.value,
                loginId = admin.loginId,
            )
        }
    }
}