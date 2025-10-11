package org.example.marketing.dto.board.request

data class UploadAdvertisementImageRequestFromClient(
    val advertisementId: Long,
    val isThumbnail: Boolean
)
