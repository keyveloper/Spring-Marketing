package org.example.marketing.dto.like.response

import org.example.marketing.enums.LikeStatus

data class LikeResult(
    val likedAdvertisement: LikedAdvertisement?
) {
    companion object {
        fun of(
            likedAdvertisement: LikedAdvertisement?
        ): LikeResult {
            return LikeResult(
                likedAdvertisement
            )
        }
    }
}
