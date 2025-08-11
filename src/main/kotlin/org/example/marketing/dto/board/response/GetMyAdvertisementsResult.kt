package org.example.marketing.dto.board.response

import org.example.marketing.domain.board.Advertisement

data class GetMyAdvertisementsResult(
    val advertisements: List<MyAdvertisementInfo>
) {
    companion object {
        fun of(advertisements: List<Advertisement>): GetMyAdvertisementsResult {
            return GetMyAdvertisementsResult(
                advertisements = advertisements.map { MyAdvertisementInfo.of(it) }
            )
        }
    }
}

