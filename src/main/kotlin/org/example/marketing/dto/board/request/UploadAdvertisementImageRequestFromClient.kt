package org.example.marketing.dto.board.request

data class UploadAdvertisementImageRequestFromClient(
    val advertisementDraftId: Long,
    val isThumbnail: Boolean,
)
