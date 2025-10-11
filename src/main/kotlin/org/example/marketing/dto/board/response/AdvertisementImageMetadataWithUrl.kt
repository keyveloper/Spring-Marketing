package org.example.marketing.dto.board.response

import kotlin.Long

data class AdvertisementImageMetadataWithUrl(
    val advertisementId: Long,
    val writerId: Long,
    val presignedUrl: String?,
    val bucketName: String?,
    val s3Key: String?,
    val contentType: String?,
    val size: Long?,
    val originalFileName: String?,
    val isThumbnail: Boolean?
) {
    companion object {
        fun of(
            advertisementId: Long,
            writerId: Long,
            presignedUrl: String,
            bucketName: String,
            s3Key: String,
            contentType: String,
            size: Long,
            originalFileName: String? = null,
            isThumbnail: Boolean = false
        ): AdvertisementImageMetadataWithUrl {
            return AdvertisementImageMetadataWithUrl(
                advertisementId = advertisementId,
                writerId = writerId,
                presignedUrl = presignedUrl,
                bucketName = bucketName,
                s3Key = s3Key,
                contentType = contentType,
                size = size,
                originalFileName = originalFileName,
                isThumbnail = isThumbnail
            )
        }
    }
}