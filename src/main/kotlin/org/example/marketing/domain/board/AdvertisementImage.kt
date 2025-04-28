package org.example.marketing.domain.board

import org.example.marketing.dao.board.AdvertisementImageEntity
import org.example.marketing.enums.EntityLiveStatus

data class AdvertisementImage(
    val id: Long,
    val advertisementId: Long,
    val originalFileName: String,
    val convertedFileName: String,
    val apiCallUrl: String,
    val filePath: String,
    val fileSizeKB: Long,
    val fileType: String,
    val isThumbnail: Boolean,
    val createdAt: Long,
    val updatedAt: Long
) {
    companion object {
        fun of(entity: AdvertisementImageEntity): AdvertisementImage {
            return AdvertisementImage(
                id = entity.id.value,
                advertisementId = entity.advertisementId,
                originalFileName = entity.originalFileName,
                convertedFileName = entity.convertedFileName,
                apiCallUrl = entity.apiCallUrl,
                filePath = entity.filePath,
                fileSizeKB = entity.fileSizeKB,
                fileType = entity.fileType,
                isThumbnail = entity.isThumbnail,
                createdAt = entity.createdAt,
                updatedAt = entity.updatedAt
            )
        }
    }
}
