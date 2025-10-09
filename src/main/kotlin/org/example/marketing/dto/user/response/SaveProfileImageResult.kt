package org.example.marketing.dto.user.response

data class SaveProfileImageResult(
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
        ): SaveProfileImageResult {
            return SaveProfileImageResult(id, s3Key, bucketName, contentType, size, originalFileName)
        }
    }
}