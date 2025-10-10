package org.example.marketing.domain.board

data class AdvertisementImageInfo(
    val id: Long,
    val s3Key: String,
    val bucketName: String,
    val contentType: String,
    val size: Long,
    val originalFileName: String?
)