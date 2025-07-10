package org.example.marketing.dto.user.response

data class SaveInfluencerProfileImageResult(
    val id: Long,
    val userId: String,
    val s3Key: String,
    val bucketName: String,
    val contentType: String,
    val size: Long,
    val originalFileName: String?
)
