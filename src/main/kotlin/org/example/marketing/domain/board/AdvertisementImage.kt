package org.example.marketing.domain.board

import org.example.marketing.dao.board.AdvertisementImageEntity

data class AdvertisementImage(
    val entityId: Long,
    val advertisementId: Long?,
    val apiCallUri: String,
    val createdAt: Long,
    val draftId: Long
) {
    companion object {
        fun of(entity: AdvertisementImageEntity): AdvertisementImage {
            return AdvertisementImage(
                entityId = entity.id.value,
                advertisementId = entity.advertisementId,
                apiCallUri = entity.apiCallUri,
                createdAt = entity.createdAt,
                draftId = entity.draftId
            )
        }
    }
}
