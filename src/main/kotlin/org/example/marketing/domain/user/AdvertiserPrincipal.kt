package org.example.marketing.domain.user

import org.example.marketing.dao.user.AdvertiserEntity
import org.example.marketing.enums.UserType

data class AdvertiserPrincipal(
    override val userId: Long, // entity id : Loog
    override val loginId: String,
    override val userType: UserType = UserType.ADVERTISER_COMMON
): CustomUserPrincipal {
    companion object {
        fun of(admin: AdvertiserEntity): AdvertiserPrincipal {
            return AdvertiserPrincipal(
                userId = admin.id.value,
                loginId = admin.loginId,
            )
        }
    }
}
