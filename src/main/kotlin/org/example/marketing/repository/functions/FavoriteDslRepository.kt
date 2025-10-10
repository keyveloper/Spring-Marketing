package org.example.marketing.repository.functions

import org.example.marketing.dao.functions.InfluencerFavoriteAdWithImageEntity
import org.example.marketing.enums.AdvertisementStatus
import org.example.marketing.enums.DraftStatus
import org.example.marketing.enums.EntityLiveStatus
import org.example.marketing.enums.FavoriteStatus
import org.example.marketing.table.AdvertisementDraftsTable
import org.example.marketing.table.AdvertisementsTable
import org.example.marketing.table.InfluencerFavoritesAdTable
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.selectAll
import org.springframework.stereotype.Component

@Component
class FavoriteDslRepository {
    fun findAllFavoriteAdByInfluencerId(influencerId: Long): List<InfluencerFavoriteAdWithImageEntity> {
        val joinedTable = InfluencerFavoritesAdTable
            .join(
                otherTable = AdvertisementsTable,
                joinType = JoinType.INNER,
                onColumn = InfluencerFavoritesAdTable.advertisementId,
                otherColumn = AdvertisementsTable.id,
                additionalConstraint = {
                    (AdvertisementsTable.status eq AdvertisementStatus.LIVE) and
                            (InfluencerFavoritesAdTable.influencerId eq influencerId) and
                            (InfluencerFavoritesAdTable.favoriteStatus eq FavoriteStatus.FAVORITE)
                }
            )

        val result = joinedTable
            .selectAll()
            .map { InfluencerFavoriteAdWithImageEntity.fromRow(it) }

        return result
    }
}