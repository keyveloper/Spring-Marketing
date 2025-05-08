package org.example.marketing.repository.functions

import org.example.marketing.dao.functions.InfluencerFavoriteAdWithImageEntity
import org.example.marketing.enums.DraftStatus
import org.example.marketing.enums.EntityLiveStatus
import org.example.marketing.enums.FavoriteStatus
import org.example.marketing.table.AdvertisementDraftsTable
import org.example.marketing.table.AdvertisementImagesTable
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
                otherTable = AdvertisementImagesTable,
                joinType = JoinType.INNER,
                onColumn = InfluencerFavoritesAdTable.advertisementId,
                otherColumn = AdvertisementImagesTable.advertisementId,
                additionalConstraint = {
                    (AdvertisementImagesTable.liveStatus eq EntityLiveStatus.LIVE) and
                            (InfluencerFavoritesAdTable.influencerId eq influencerId) and
                            (InfluencerFavoritesAdTable.favoriteStatus eq FavoriteStatus.FAVORITE) and
                            (AdvertisementImagesTable.isThumbnail eq true)
                }
            ).join(
                otherTable = AdvertisementDraftsTable,
                joinType = JoinType.INNER,
                onColumn = AdvertisementImagesTable.draftId,
                otherColumn = AdvertisementDraftsTable.id,
                additionalConstraint = {
                    (AdvertisementDraftsTable.draftStatus eq DraftStatus.SAVED)
                }
            )

        val result = joinedTable
            .selectAll()
            .map { InfluencerFavoriteAdWithImageEntity.fromRow(it) }

        return result
    }
}