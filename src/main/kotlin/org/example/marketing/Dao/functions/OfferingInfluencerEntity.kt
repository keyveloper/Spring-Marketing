package org.example.marketing.dao.functions

import org.example.marketing.table.InfluencerProfileImagesTable
import org.example.marketing.table.InfluencersTable
import org.example.marketing.table.ReviewOffersTable
import org.jetbrains.exposed.sql.ResultRow

data class OfferingInfluencerEntity(
    val influencerId: Long,
    val influencerLoginId: String,
    val influencerMainProfileImageUrl: String?,
    val offer: String,
    val offerCreatedAt: Long,
) {
    companion object {
        fun fromRow(row: ResultRow): OfferingInfluencerEntity =
            OfferingInfluencerEntity(
                influencerId  = row[ReviewOffersTable.influencerId],
                influencerLoginId = row[InfluencersTable.loginId],
                influencerMainProfileImageUrl = row[InfluencerProfileImagesTable.unifiedCode],
                offer         = row[ReviewOffersTable.offer],
                offerCreatedAt = row[ReviewOffersTable.createdAt]
            )
    }
}
