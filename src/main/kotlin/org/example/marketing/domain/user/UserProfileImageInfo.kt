package org.example.marketing.domain.user

import org.example.marketing.enums.ProfileImageType

data class UserProfileImageInfo(
    val id: Long,
    val s3Key: String,
    val bucketName: String,
    val contentType: String,
    val size: Long,
    val originalFileName: String?
)