package org.example.marketing.dto.board.request

data class SetAdvertisementThumbnailRequest(
    val entityId: Long,
    val advertisementId: Long
)