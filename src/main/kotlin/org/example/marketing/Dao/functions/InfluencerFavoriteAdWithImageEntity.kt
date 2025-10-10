package org.example.marketing.dao.functions

import org.example.marketing.enums.FavoriteStatus
import org.example.marketing.table.AdvertisementsTable
import org.example.marketing.table.InfluencerFavoritesAdTable
import org.jetbrains.exposed.sql.ResultRow

data class InfluencerFavoriteAdWithImageEntity(
    val advertisementId: Long,
    val favoriteStatus: FavoriteStatus,
) {
    companion object {
        fun fromRow(row: ResultRow): InfluencerFavoriteAdWithImageEntity {
            return InfluencerFavoriteAdWithImageEntity(
                advertisementId = row[AdvertisementsTable.id].value,
                favoriteStatus = row[InfluencerFavoritesAdTable.favoriteStatus],
            )
        }
    }
}