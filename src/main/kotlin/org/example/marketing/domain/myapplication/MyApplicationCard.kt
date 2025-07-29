package org.example.marketing.domain.myapplication

import org.example.marketing.dto.board.response.ThumbnailMetadataWithUrl

data class MyApplicationCard(
    val application: MyAppliedAdvertisement,
    val thumbnail: ThumbnailMetadataWithUrl?
)
