package org.example.marketing.domain.user

import org.example.marketing.dao.user.InfluencerProfileInfoEntity

data class InfluencerProfileInfo(
    val id: Long,
    val influencerId: Long,
    val introduction: String?,
    val job: String?,
    val createdAt: Long,
    val updatedAt: Long,
) {
    companion object {
        fun of(entity: InfluencerProfileInfoEntity): InfluencerProfileInfo {
            return InfluencerProfileInfo(
                id = entity.id.value,
                influencerId = entity.influencerId,
                introduction = entity.introduction,
                job = entity.job,
                createdAt = entity.createdAt,
                updatedAt = entity.updatedAt
            )
        }
    }
}
