package org.example.marketing.repository.functions

import org.example.marketing.dao.functions.InfluencerFavoriteAdEntity
import org.example.marketing.dto.functions.request.SaveInfluencerFavoriteAd
import org.example.marketing.enums.FavoriteStatus
import org.example.marketing.exception.DuplicatedInfluencerFavoriteAdException
import org.example.marketing.table.InfluencerFavoritesAdTable
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.and
import org.springframework.stereotype.Component
import java.sql.SQLException

@Component
class FavoriteRepository {
    fun switchById(targetId: Long): InfluencerFavoriteAdEntity {
        val favoriteEntity = InfluencerFavoriteAdEntity.find {
            InfluencerFavoritesAdTable.id eq targetId
        }.single()

        if (favoriteEntity.favoriteStatus == FavoriteStatus.FAVORITE) {
            favoriteEntity.favoriteStatus = FavoriteStatus.DEAD
        } else {
            favoriteEntity.favoriteStatus = FavoriteStatus.FAVORITE
        }
        return favoriteEntity
    }

    fun save(saveInfluencerFavoriteAd: SaveInfluencerFavoriteAd): InfluencerFavoriteAdEntity {

        val favoriteEntity = InfluencerFavoriteAdEntity.new {
            influencerId = saveInfluencerFavoriteAd.influencerId
            advertisementId = saveInfluencerFavoriteAd.advertisementId
            favoriteStatus = FavoriteStatus.FAVORITE
        }
        return favoriteEntity
    }

    fun findByInfluencerId(influencerId: Long): List<InfluencerFavoriteAdEntity> {
        return InfluencerFavoriteAdEntity.find {
            (InfluencerFavoritesAdTable.influencerId eq influencerId) and
                    (InfluencerFavoritesAdTable.favoriteStatus eq FavoriteStatus.FAVORITE)
        }.toList()
    }

    fun checkEntityExist(influencerId: Long, advertisementId: Long): InfluencerFavoriteAdEntity? {
        return InfluencerFavoriteAdEntity.find {
            (InfluencerFavoritesAdTable.influencerId eq influencerId) and
                    (InfluencerFavoritesAdTable.advertisementId eq advertisementId)
        }.singleOrNull()
    }
}