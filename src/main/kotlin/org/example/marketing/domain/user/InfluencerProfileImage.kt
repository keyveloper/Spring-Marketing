package org.example.marketing.domain.user

import org.example.marketing.dao.user.InfluencerProfileImageEntity

data class InfluencerProfileImage(
    val id: Long,
    val influencerId: Long,
    val unifiedCode: String,
) {
    companion object {
        fun of(entity: InfluencerProfileImageEntity): InfluencerProfileImage {
            return InfluencerProfileImage(
                id = entity.id.value,
                influencerId = entity.influencerId,
                unifiedCode = entity.unifiedCode,
            )
        }
    }
}
