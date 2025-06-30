package org.example.marketing.dto.board.request

data class UploadAdvertisementImageApiRequest(
    val advertisementDraftId: Long,
    val writerId: String,
    val isThumbnail: Boolean,
) {
    companion object {
        fun of(
            advertisementDraftId: Long,
            writerId: String,
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
