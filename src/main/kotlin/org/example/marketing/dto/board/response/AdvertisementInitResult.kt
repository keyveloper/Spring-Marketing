package org.example.marketing.dto.board.response

data class AdvertisementInitResult(
    val freshAds: List<ThumbnailAdCard>,
    val deadlineAds: List<ThumbnailAdCard>
) {
    companion object {
        fun of(
            freshAds: List<ThumbnailAdCard>,
            deadlineAds: List<ThumbnailAdCard>
        ): AdvertisementInitResult {
            return AdvertisementInitResult(
                freshAds = freshAds,
                deadlineAds = deadlineAds
            )
        }
    }
}
