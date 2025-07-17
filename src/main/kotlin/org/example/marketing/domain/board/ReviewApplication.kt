package org.example.marketing.domain.board

import org.example.marketing.dao.board.ReviewApplicationEntity
import java.util.UUID

data class ReviewApplication(
    val id: Long,
    val influencerId: UUID,
    val influencerUsername: String,
    val advertisementId: Long,
    val applyMemo: String,
    val createdAt: Long,
) {
    companion object {
        fun of(entity: ReviewApplicationEntity): ReviewApplication {
            return ReviewApplication(
                id = entity.id.value,
                influencerId = entity.influencerId,
                influencerUsername = entity.influencerUsername,
                advertisementId = entity.advertisementId,
                applyMemo = entity.applyMemo,
                createdAt = entity.createdAt,
            )
        }
    }
}