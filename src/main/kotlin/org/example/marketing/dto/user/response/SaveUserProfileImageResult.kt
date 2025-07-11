package org.example.marketing.dto.user.response

data class SaveUserProfileImageResult(
    val id: Long,
    val s3Key: String,
    val bucketName: String,
    val contentType: String,
    val size: Long,
    val originalFileName: String?
) {
    companion object {
        fun of(
            id: Long,
            s3Key: String,
            bucketName: String,
            contentType: String,
            size: Long,
            originalFileName: String?
        ): SaveUserProfileImageResult {
            return SaveUserProfileImageResult(id, s3Key, bucketName, contentType, size, originalFileName)
        }
    }
}