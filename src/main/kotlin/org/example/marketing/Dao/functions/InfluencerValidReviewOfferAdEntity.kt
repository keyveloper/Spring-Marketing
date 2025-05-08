package org.example.marketing.dao.functions

import org.example.marketing.enums.ReviewOfferStatus
import org.example.marketing.table.AdvertisementImagesTable
import org.example.marketing.table.ReviewOffersTable
import org.jetbrains.exposed.sql.ResultRow

data class InfluencerValidReviewOfferAdEntity(
    val advertisementId: Long,
    val influencerId: Long,
    val thumbnailUrl: String?,
    val offerStatus: ReviewOfferStatus
) {
    companion object {
        fun fromRow(row: ResultRow): InfluencerValidReviewOfferAdEntity {
            return InfluencerValidReviewOfferAdEntity(
                advertisementId = row[ReviewOffersTable.advertisementId],
                influencerId = row[ReviewOffersTable.influencerId],
                thumbnailUrl = row[AdvertisementImagesTable.apiCallUri],
                offerStatus = row[ReviewOffersTable.offerStatus]
            )
        }
    }
}