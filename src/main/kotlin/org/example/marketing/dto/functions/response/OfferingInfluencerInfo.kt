package org.example.marketing.dto.functions.response

import org.example.marketing.dao.functions.ReviewOfferEntity
import org.example.marketing.dao.user.InfluencerEntity
import org.example.marketing.table.InfluencersTable
import org.example.marketing.table.ReviewOffersTable
import org.jetbrains.exposed.sql.ResultRow

data class OfferingInfluencerInfo(
    val influencerId: Long,
    val influencerLoginId: String,
    val influencerMainProfileImageUrl: String?,
    val offer: String,
    val offerCreatedAt: Long,
) {
    companion object {
        fun fromRow(row: ResultRow): OfferingInfluencerInfo =
            OfferingInfluencerInfo(
                influencerId  = row[ReviewOffersTable.influencerId],
                influencerLoginId = row[InfluencersTable.loginId],
                influencerMainProfileImageUrl = null,
                offer         = row[ReviewOffersTable.offer],
                offerCreatedAt = row[ReviewOffersTable.createdAt]
            )
    }
}
