package org.example.marketing.dto.board.request

data class UploadAdvertisementImageApiRequest(
    val isThumbnail: Boolean
) {
    companion object {
        fun of(
            isThumbnail: Boolean
        ): UploadAdvertisementImageApiRequest {
            return UploadAdvertisementImageApiRequest(
                isThumbnail = isThumbnail
            )
        }
    }
}
