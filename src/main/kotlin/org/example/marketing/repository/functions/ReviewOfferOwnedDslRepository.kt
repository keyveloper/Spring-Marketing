package org.example.marketing.repository.functions

import org.example.marketing.dao.functions.InfluencerValidReviewOfferAdEntity
import org.example.marketing.enums.DraftStatus
import org.example.marketing.enums.EntityLiveStatus
import org.example.marketing.enums.ReviewOfferStatus
import org.example.marketing.table.AdvertisementDraftsTable
import org.example.marketing.table.AdvertisementImagesTable
import org.example.marketing.table.ReviewOffersTable
import org.jetbrains.exposed.sql.*
import org.springframework.stereotype.Component

@Component
class ReviewOfferOwnedDslRepository {
    fun findAllValidOfferByInfluencerId(influencerId: Long): List<InfluencerValidReviewOfferAdEntity> {
        val joinedTable: ColumnSet = ReviewOffersTable
            .join(
                otherTable = AdvertisementImagesTable,
                joinType = JoinType.INNER,
                onColumn = ReviewOffersTable.advertisementId,
                otherColumn = AdvertisementImagesTable.advertisementId,
                additionalConstraint = {
                    ((ReviewOffersTable.offerStatus eq ReviewOfferStatus.PROGRESSING) or
                            (ReviewOffersTable.offerStatus eq ReviewOfferStatus.COMPLETED) or
                            (ReviewOffersTable.offerStatus eq ReviewOfferStatus.OFFER)) and
                            (ReviewOffersTable.influencerId eq influencerId) and
                            (AdvertisementImagesTable.liveStatus eq EntityLiveStatus.LIVE)
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

        val query = joinedTable.selectAll()
        val result = query.map { row ->
            InfluencerValidReviewOfferAdEntity.fromRow(row)
        }

        return result
    }
}