package org.example.marketing.dto.board.request

import java.util.UUID

data class UploadAdvertisementImageRequestFromClient(
    val advertisementDraftId: UUID,
    val isThumbnail: Boolean,
)
