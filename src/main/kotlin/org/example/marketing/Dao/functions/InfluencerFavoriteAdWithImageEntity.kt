package org.example.marketing.dao.functions

import org.example.marketing.enums.FavoriteStatus
import org.example.marketing.table.AdvertisementImagesTable
import org.example.marketing.table.InfluencerFavoritesAdTable
import org.jetbrains.exposed.sql.ResultRow

data class InfluencerFavoriteAdWithImageEntity(
    val advertisementId: Long,
    val favoriteStatus: FavoriteStatus,
    val imageUrl: String,
    val isThumbnail: Boolean
) {
    companion object {
        fun fromRow(row: ResultRow): InfluencerFavoriteAdWithImageEntity {
            return InfluencerFavoriteAdWithImageEntity(
                advertisementId = row[AdvertisementImagesTable.advertisementId]!!,
                favoriteStatus = row[InfluencerFavoritesAdTable.favoriteStatus],
                imageUrl = row[AdvertisementImagesTable.apiCallUri],
                isThumbnail = row[AdvertisementImagesTable.isThumbnail]
            )
        }
    }
}