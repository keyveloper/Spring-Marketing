package org.example.marketing.domain.board

import org.example.marketing.dao.board.AdvertisementDraftEntity
import org.example.marketing.enums.DraftStatus

data class AdvertisementDraft(
    val id: Long,
    val advertiserId: String,
    val draftStatus: DraftStatus,
    val expiredAt: Long,
    val createdAt: Long,
    val updatedAt: Long
) {
    companion object {
        fun of(entity: AdvertisementDraftEntity): AdvertisementDraft {
            return AdvertisementDraft(
                id = entity.id.value,
                advertiserId = entity.advertiserId,
                draftStatus = entity.draftStatus,
                expiredAt = entity.expiredAt,
                createdAt = entity.createdAt,
                updatedAt = entity.updatedAt
            )
        }
    }
}