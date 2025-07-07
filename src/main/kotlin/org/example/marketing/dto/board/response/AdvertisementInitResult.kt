package org.example.marketing.dto.board.response

data class AdvertisementInitResult(
    val thumbnailAdCards: List<ThumbnailAdCard>,
) {
    companion object {
        fun of(
            thumbnailAdCards: List<ThumbnailAdCard>,
        ): AdvertisementInitResult {
            return AdvertisementInitResult(
              thumbnailAdCards = thumbnailAdCards,
            )
        }
    }
}
