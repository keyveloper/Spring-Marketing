package org.example.marketing.domain.functions

import org.example.marketing.dao.functions.InfluencerFavoriteAdWithImageEntity
import org.example.marketing.enums.FavoriteStatus

data class InfluencerFavoriteAdWithThumbnail(
    val advertisementId: Long,
    val favoriteStatus: FavoriteStatus
) {
    companion object {
        fun of(entity: InfluencerFavoriteAdWithImageEntity): InfluencerFavoriteAdWithThumbnail {
            return InfluencerFavoriteAdWithThumbnail(
                advertisementId = entity.advertisementId,
                favoriteStatus = entity.favoriteStatus
            )
        }
    }
}
