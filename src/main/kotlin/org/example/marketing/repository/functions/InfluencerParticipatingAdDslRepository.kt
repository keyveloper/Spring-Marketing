package org.example.marketing.repository.functions

import org.example.marketing.dao.functions.InfluencerParticipatingAdEntity
import org.example.marketing.enums.AdvertisementStatus
import org.example.marketing.enums.DraftStatus
import org.example.marketing.enums.EntityLiveStatus
import org.example.marketing.table.AdvertisementDraftsTable
import org.example.marketing.table.AdvertisementImagesTable
import org.example.marketing.table.AdvertisementsTable
import org.example.marketing.table.ReviewOffersTable
import org.jetbrains.exposed.sql.*
import org.springframework.stereotype.Component

@Component
class InfluencerParticipatingAdDslRepository {
    fun findAllAdsParticipatedByInfluencerId(influencerId: Long): List<InfluencerParticipatingAdEntity> {
        val joinedTable: ColumnSet = ReviewOffersTable
            .join(
                otherTable = AdvertisementsTable,
                joinType = JoinType.INNER,
                onColumn = ReviewOffersTable.advertisementId,
                otherColumn = AdvertisementsTable.id,
                additionalConstraint = {
                    (ReviewOffersTable.influencerId eq influencerId) and
                            (AdvertisementsTable.status eq AdvertisementStatus.LIVE)
                }
            ).join(
                otherTable = AdvertisementImagesTable,
                joinType = JoinType.INNER,
                onColumn = AdvertisementsTable.id,
                otherColumn = AdvertisementImagesTable.advertisementId,
                additionalConstraint = {
                    (AdvertisementsTable.status eq AdvertisementStatus.LIVE) and
                            (AdvertisementImagesTable.liveStatus eq EntityLiveStatus.LIVE)
                }
            ).join(
                otherTable = AdvertisementDraftsTable,
                joinType = JoinType.INNER,
                onColumn = AdvertisementsTable.id,
                otherColumn = AdvertisementDraftsTable.id,
                additionalConstraint = {
                    AdvertisementDraftsTable.draftStatus eq DraftStatus.SAVED
                }
            )

        val query = joinedTable.selectAll()
        val result = query.map { row ->
            InfluencerParticipatingAdEntity.fromRow(row)
        }

        return result
    }
}