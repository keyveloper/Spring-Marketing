package org.example.marketing.dto.functions.request

data class SaveInfluencerFavoriteAd(
    val advertisementId: Long,
    val influencerId: Long
) {
    companion object {
        fun of(
            request: FavoriteAdRequest,
            influencerId: Long,
        ): SaveInfluencerFavoriteAd {
            return SaveInfluencerFavoriteAd(
                request.advertisementId,
                influencerId
            )
        }
    }
}