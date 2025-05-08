package org.example.marketing.domain.functions

import org.example.marketing.dao.functions.InfluencerValidReviewOfferAdEntity
import org.example.marketing.enums.ReviewOfferStatus


data class InfluencerValidReviewOfferAd(
    val advertisementId: Long,
    val offerStatus: ReviewOfferStatus,
    val thumbnailUrl: String?
) {
    companion object {
        fun of(entity: InfluencerValidReviewOfferAdEntity): InfluencerValidReviewOfferAd =
            InfluencerValidReviewOfferAd(
                entity.advertisementId,
                entity.offerStatus,
                entity.thumbnailUrl
            )
    }
}
