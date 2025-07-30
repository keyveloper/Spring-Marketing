package org.example.marketing.dto.user.response

import org.example.marketing.enums.ProfileImageType
import org.example.marketing.enums.UserType
import java.util.UUID

data class UserProfileImageMetadataWithUrl(
    val userId: UUID,
    val userType: UserType,
    val profileImageType: ProfileImageType,
    val presignedUrl: String?,
    val bucketName: String?,
    val s3Key: String?,
    val contentType: String?,
    val size: Long?,
    val originalFileName: String?
)
