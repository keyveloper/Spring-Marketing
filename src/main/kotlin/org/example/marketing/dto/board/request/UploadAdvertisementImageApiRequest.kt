package org.example.marketing.dto.board.request

import java.util.UUID

data class UploadAdvertisementImageApiRequest(
    val writerId: UUID,
    val advertisementDraftId: Long,
    val isThumbnail: Boolean,
) {
    companion object {
        fun of(
            writerId: UUID,
            advertisementDraftId: Long,
            isThumbnail: Boolean,
        ): UploadAdvertisementImageApiRequest {
            return UploadAdvertisementImageApiRequest(
                writerId = writerId,
                advertisementDraftId = advertisementDraftId,
                isThumbnail = isThumbnail
            )
        }
    }
}
