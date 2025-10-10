package org.example.marketing.dao.functions

import org.example.marketing.enums.ReviewOfferStatus
import org.example.marketing.table.AdvertisementsTable
import org.example.marketing.table.ReviewOffersTable
import org.jetbrains.exposed.sql.ResultRow

data class InfluencerParticipatingAdEntity(
    val advertisementId: Long,
    val influencerId: Long,
    val offerStatus: ReviewOfferStatus
) {
    companion object {
        fun fromRow(row: ResultRow): InfluencerParticipatingAdEntity {
            return InfluencerParticipatingAdEntity(
                advertisementId = row[AdvertisementsTable.id].value,
                influencerId = row[ReviewOffersTable.influencerId],
                offerStatus = row[ReviewOffersTable.offerStatus]
            )
        }
    }
}