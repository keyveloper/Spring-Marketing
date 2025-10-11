package org.example.marketing.dto.board.request

data class UploadAdvertisementImageApiRequest(
    val advertisementId: Long,
    val writerId: Long,
    val isThumbnail: Boolean
) {
    companion object {
        fun of(
            advertisementId: Long,
            writerId: Long,
            isThumbnail: Boolean,
        ): UploadAdvertisementImageApiRequest {
            return UploadAdvertisementImageApiRequest(
                advertisementId = advertisementId,
                writerId = writerId,
                isThumbnail = isThumbnail
            )
        }
    }
}
