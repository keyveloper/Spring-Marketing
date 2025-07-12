package org.example.marketing.dto.board.request

import java.util.UUID


data class ConnectAdvertisementIdRequest(
    val draftId: UUID,
    val advertisementId: Long
)