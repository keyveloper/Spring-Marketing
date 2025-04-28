package org.example.marketing.domain.functions

import org.example.marketing.dao.functions.InfluencerFavoriteAdEntity

data class InfluencerFavoriteAds(
    val advertisementId: Long,
    val influencerId: Long,
) {
    companion object {
        fun of(entity: InfluencerFavoriteAdEntity): InfluencerFavoriteAds {
            return InfluencerFavoriteAds(
                advertisementId = entity.advertisementId,
                influencerId = entity.influencerId,
            )
        }
    }
}