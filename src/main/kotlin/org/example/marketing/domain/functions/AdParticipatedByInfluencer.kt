package org.example.marketing.domain.functions

import org.example.marketing.dao.functions.InfluencerParticipatingAdEntity
import org.example.marketing.enums.ReviewOfferStatus


data class AdParticipatedByInfluencer(
    val advertisementId: Long,
    val offerStatus: ReviewOfferStatus,
    val thumbnailUrl: String?
) {
    companion object {
        fun of(entity: InfluencerParticipatingAdEntity): AdParticipatedByInfluencer =
            AdParticipatedByInfluencer(
                entity.advertisementId,
                entity.offerStatus,
                entity.thumbnailUrl
            )
    }
}
