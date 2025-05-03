package org.example.marketing.dto.board.request

data class SetAdvertisementThumbnailRequest(
    val imageId: Long,
    val advertisementId: Long
)