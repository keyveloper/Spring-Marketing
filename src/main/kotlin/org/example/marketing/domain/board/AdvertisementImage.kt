package org.example.marketing.domain.board

import org.example.marketing.dao.board.AdvertisementImageEntity

data class AdvertisementImage(
    val id: Long,
    val advertisementId: Long,
    val apiCallUri: String,
    val fileSizeKB: Long,
    val fileType: String,
    val isThumbnail: Boolean,
    val createdAt: Long,
) {
    companion object {
        fun of(entity: AdvertisementImageEntity): AdvertisementImage {
            return AdvertisementImage(
                id = entity.id.value,
                advertisementId = entity.advertisementId,
                apiCallUri = entity.apiCallUri,
                fileSizeKB = entity.fileSizeKB,
                fileType = entity.fileType,
                isThumbnail = entity.isThumbnail,
                createdAt = entity.createdAt,
            )
        }
    }
}
