package org.example.marketing.dto.board.response

data class ThumbnailMetadataWithUrl(
    val advertisementId: Long,
    val advertisementImageMetaId: Long,
    val presignedUrl: String,
    val bucketName: String,
    val s3Key: String,
    val contentType: String,
    val size: Long
)
