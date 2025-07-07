package org.example.marketing.domain.user

import org.example.marketing.dao.user.AdvertiserEntity
import org.example.marketing.enums.UserType

data class AdminPrincipal(
    override val userId: Long,
    override val loginId: String,
): CustomUserPrincipal(
    userId = userId,
    loginId = loginId,
    userType = UserType.ADMIN
) {
    companion object {
        fun of(admin: AdminEntity): AdminPrincipal {
            return AdminPrincipal(
                userId = admin.id.value,
                loginId = admin.loginId,
            )
        }
    }
}