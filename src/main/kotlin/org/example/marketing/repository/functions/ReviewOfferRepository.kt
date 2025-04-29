package org.example.marketing.repository.functions

import org.example.marketing.dao.functions.ReviewOfferEntity
import org.example.marketing.dto.functions.request.SaveReviewOffer
import org.example.marketing.dto.functions.response.OfferingInfluencerInfo
import org.example.marketing.enums.EntityLiveStatus
import org.example.marketing.table.InfluencersTable
import org.example.marketing.table.ReviewOffersTable
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.varPop
import org.springframework.stereotype.Component

@Component
class ReviewOfferRepository {
    fun save(saveReviewOffer: SaveReviewOffer): ReviewOfferEntity {
        val reviewOfferEntity = ReviewOfferEntity.new {
            advertisementId = saveReviewOffer.advertisementId
            influencerId = saveReviewOffer.influencerId
            offer = saveReviewOffer.offer
            liveStatus = EntityLiveStatus.LIVE
        }

        return reviewOfferEntity
    }

    fun checkLiveEntityExist(advertisementId: Long, influencerId: Long): Boolean {
        return ReviewOfferEntity.find {
            (ReviewOffersTable.influencerId eq influencerId) and
                    (ReviewOffersTable.advertisementId eq advertisementId) and
                    (ReviewOffersTable.liveStatus eq EntityLiveStatus.LIVE)
        }.any()
    }

    fun findByAdvertisementId(advertisementId: Long): List<ReviewOfferEntity> {
        return ReviewOfferEntity.find {
            (ReviewOffersTable.advertisementId eq advertisementId) and
                    (ReviewOffersTable.liveStatus eq EntityLiveStatus.LIVE)
        }.toList()
    }

    fun findOfferingInfluencerInfos(advertisementId: Long): List<OfferingInfluencerInfo> = transaction {
        ReviewOffersTable.join(
            otherTable = InfluencersTable,
            joinType = JoinType.INNER,
            onColumn = ReviewOffersTable.influencerId,
            otherColumn = InfluencersTable.id,
            additionalConstraint = { ReviewOffersTable.advertisementId eq advertisementId }
        ).select(
            ReviewOffersTable.influencerId,
            ReviewOffersTable.offer,
            ReviewOffersTable.createdAt,
            InfluencersTable.loginId,
        ).map(OfferingInfluencerInfo::fromRow)
    }
}