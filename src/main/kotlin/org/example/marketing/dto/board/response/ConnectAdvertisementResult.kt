package org.example.marketing.dto.board.response

data class ConnectAdvertisementResult(
    val updatedRow: Int,
    val connectedS3BucketKeys: List<String>
)