package org.example.marketing.domain.functions

import org.example.marketing.dao.functions.ReviewApplicationEntity
import java.util.UUID

data class ReviewApplication(
    val id: Long,
    val influencerId: UUID,
    val influencerUsername: String,
    val influencerEmail: String,
    val influencerMobile: String,
    val advertisementId: Long,
    val applyMemo: String,
    val createdAt: Long,
    val updatedAt: Long
) {
    companion object {
        fun of(entity: ReviewApplicationEntity): ReviewApplication {
            return ReviewApplication(
                id = entity.id.value,
                influencerId = entity.influencerId,
                influencerUsername = entity.influencerUsername,
                influencerEmail = entity.influencerEmail,
                influencerMobile = entity.influencerMobile,
                advertisementId = entity.advertisementId,
                applyMemo = entity.applyMemo,
                createdAt = entity.createdAt,
                updatedAt = entity.updatedAt
            )
        }
    }
}
