package org.example.marketing.table

import org.example.marketing.enums.FavoriteStatus
import org.jetbrains.exposed.sql.Column

object InfluencerFavoritesAdTable: BaseDateLongIdTable("influencer_favorite_ads") {
    val advertisementId: Column<Long> = long("advertisement_id").index()
    val influencerId: Column<Long> = long("influencer_id").index()
    val favoriteStatus: Column<FavoriteStatus> =
        enumerationByName("favorite_status", 255, FavoriteStatus::class)

    init {
        uniqueIndex("uk_influencer_advertisement", advertisementId, influencerId)
    }
}