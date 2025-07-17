package org.example.marketing.dto.board.request

import java.util.UUID

data class SaveReviewApplication(
    val influencerId: UUID,
    val influencerUsername: String,
    val influencerEmail: String,
    val influencerMobile: String,
    val advertisementId: Long,
    val applyMemo: String
) {
    companion object {
        fun of(
            request: ApplyReviewRequest,
            influencerId: UUID,
            influencerUsername: String,
            influencerEmail: String,
            influencerMobile: String
        ): SaveReviewApplication {
            return SaveReviewApplication(
                influencerId = influencerId,
                influencerUsername = influencerUsername,
                influencerEmail = influencerEmail,
                influencerMobile = influencerMobile,
                advertisementId = request.advertisementId,
                applyMemo = request.applyMemo
            )
        }
    }
}