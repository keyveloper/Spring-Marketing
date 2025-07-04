package org.example.marketing.dto.board.response

data class ThumbnailResult(
    val imageMetaId: Long,
    val thumbnailMetaId: Long,
    val originalS3Key: String,
    val thumbnailS3Key: String,
    val thumbnailBucketName: String,
    val thumbnailSize: Long
)
