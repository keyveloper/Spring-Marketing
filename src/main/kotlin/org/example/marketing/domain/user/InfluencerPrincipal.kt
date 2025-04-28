package org.example.marketing.domain.user

import org.example.marketing.dao.user.InfluencerEntity
import org.example.marketing.enums.UserType

data class InfluencerPrincipal(
    override val userId: Long,
    override val loginId: String,
): CustomUserPrincipal(
    userId = userId,
    loginId = loginId,
    userType = UserType.INFLUENCER,
) {
    companion object {
        fun of(
            influencerEntity: InfluencerEntity
        ): InfluencerPrincipal {
            return InfluencerPrincipal(
                userId = influencerEntity.id.value,
                loginId = influencerEntity.loginId
            )
        }
    }
}