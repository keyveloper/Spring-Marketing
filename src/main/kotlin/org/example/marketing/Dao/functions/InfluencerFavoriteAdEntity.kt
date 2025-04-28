package org.example.marketing.dao.functions

import org.example.marketing.dao.BaseDateEntity
import org.example.marketing.dao.BaseDateEntityClass
import org.example.marketing.table.InfluencerFavoritesAdTable
import org.jetbrains.exposed.dao.id.EntityID


class InfluencerFavoriteAdEntity(id: EntityID<Long>) : BaseDateEntity(id, InfluencerFavoritesAdTable) {
    companion object : BaseDateEntityClass<InfluencerFavoriteAdEntity>(InfluencerFavoritesAdTable)

    var advertisementId by InfluencerFavoritesAdTable.advertisementId
    var influencerId by InfluencerFavoritesAdTable.influencerId
    var favoriteStatus by InfluencerFavoritesAdTable.favoriteStatus
}