package org.example.marketing.domain.user

import org.example.marketing.dao.user.AdvertiserEntity
import org.example.marketing.enums.UserType
import kotlin.math.log

data class AdvertiserPrincipal(
    override val userId: Long,
    override val loginId: String,
) : CustomUserPrincipal(
    userId = userId,
    loginId = loginId,
    userType = UserType.ADVERTISER_COMMON,
) {
    companion object {
        fun of(
            advertiserEntity: AdvertiserEntity
        ): AdvertiserPrincipal {
            return AdvertiserPrincipal(
                userId = advertiserEntity.id.value,
                loginId = advertiserEntity.loginId
            )
        }
    }
}