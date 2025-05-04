package org.example.marketing.repository.functions

import org.example.marketing.dao.board.AdvertisementEntity
import org.example.marketing.dao.functions.InfluencerFavoriteAdEntity
import org.example.marketing.domain.board.AdvertisementPackage
import org.example.marketing.domain.board.AdvertisementPackageDomain
import org.example.marketing.dto.functions.request.SaveInfluencerFavoriteAd
import org.example.marketing.enums.AdvertisementStatus
import org.example.marketing.enums.EntityLiveStatus
import org.example.marketing.enums.FavoriteStatus
import org.example.marketing.table.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.springframework.stereotype.Component

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

    fun findAllAdPackageByInfluencerId(influencerId: Long): List<AdvertisementPackageDomain> {
        val joinedTables: ColumnSet = AdvertisementsTable
            .join(
                otherTable = AdvertisementImagesTable,
                joinType = JoinType.INNER,
                onColumn = AdvertisementsTable.id,
                otherColumn = AdvertisementImagesTable.advertisementId,
                additionalConstraint = {
                    (AdvertisementsTable.status eq AdvertisementStatus.LIVE) and
                            (AdvertisementImagesTable.liveStatus eq EntityLiveStatus.LIVE)
                }
            ).join(
                AdvertisementDeliveryCategoriesTable,
                JoinType.LEFT,
                onColumn = AdvertisementsTable.id,
                otherColumn = AdvertisementDeliveryCategoriesTable.advertisementId
            ).join(
                otherTable = InfluencerFavoritesAdTable,
                joinType = JoinType.INNER,
                onColumn = InfluencerFavoritesAdTable.advertisementId,
                otherColumn = AdvertisementsTable.advertiserId,
                additionalConstraint = {
                    (InfluencerFavoritesAdTable.favoriteStatus eq FavoriteStatus.FAVORITE)
                }
            )

        return joinedTables
            .selectAll()
            .map(AdvertisementPackageDomain::fromRow)
    }
}

