package org.example.marketing.dto.board.request

data class MakeNewAdvertisementImageRequest(
    val advertisementId: Long,
    val originalFileName: String,
    val isThumbnail: Boolean
)

