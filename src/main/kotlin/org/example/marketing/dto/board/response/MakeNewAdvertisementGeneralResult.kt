package org.example.marketing.dto.board.response

data class MakeNewAdvertisementGeneralResult(
    val entityId: Long,
    val connectingResultFromApiServer: ConnectAdvertisementResult
)