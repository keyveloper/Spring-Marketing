package org.example.marketing.dto.board.response

import org.example.marketing.domain.board.Advertisement

data class GetMyAdvertisementsResult(
    val myAds: List<MyAdvertisementInfoWithThumbnail>
) {
    companion object {
        fun of(myAds: List<MyAdvertisementInfoWithThumbnail>): GetMyAdvertisementsResult {
            return GetMyAdvertisementsResult(
                myAds
            )
        }
    }
}

