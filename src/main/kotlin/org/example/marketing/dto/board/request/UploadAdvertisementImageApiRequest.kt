package org.example.marketing.dto.board.request

import java.util.UUID

data class UploadAdvertisementImageApiRequest(
    val writerId: UUID,
    val advertisementDraftId: UUID,
    val isThumbnail: Boolean,
) {
    companion object {
        fun of(
            writerId: UUID,
            advertisementDraftId: UUID,
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
