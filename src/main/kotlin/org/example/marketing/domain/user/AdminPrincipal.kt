package org.example.marketing.domain.user

import org.example.marketing.dao.user.AdminEntity
import org.example.marketing.enums.UserType

data class AdminPrincipal(
    override val userId: Long,
    override val loginId: String,
    override val userType: UserType = UserType.ADMIN
): CustomUserPrincipal {
    companion object {
        fun of(admin: AdminEntity): AdminPrincipal {
            return AdminPrincipal(
                userId = admin.id.value,
                loginId = admin.loginId,
            )
        }
    }
}