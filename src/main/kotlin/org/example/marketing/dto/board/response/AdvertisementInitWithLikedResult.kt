package org.example.marketing.dto.board.response

data class AdvertisementInitWithLikedResult(
    val thumbnailAdCards: List<ThumbnailAdCardWithLikedInfo>,
) {
    companion object {
        fun of(
            thumbnailAdCards: List<ThumbnailAdCardWithLikedInfo>,
        ): AdvertisementInitWithLikedResult {
            return AdvertisementInitWithLikedResult(
                thumbnailAdCards = thumbnailAdCards,
            )
        }
    }
}
