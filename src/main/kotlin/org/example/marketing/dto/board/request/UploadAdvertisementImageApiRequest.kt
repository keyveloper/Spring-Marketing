package org.example.marketing.dto.board.request

data class UploadAdvertisementImageApiRequest(
    val advertisementDraftId: Long,
    val writerId: Long,
    val isThumbnail: Boolean,
) {
    companion object {
        fun of(
            advertisementDraftId: Long,
            writerId: Long,
            isThumbnail: Boolean,
        ): UploadAdvertisementImageApiRequest {
            return UploadAdvertisementImageApiRequest(
                advertisementDraftId = advertisementDraftId,
                writerId = writerId,
                isThumbnail = isThumbnail
            )
        }
    }
}
