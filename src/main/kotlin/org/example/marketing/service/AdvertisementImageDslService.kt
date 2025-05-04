package org.example.marketing.service

import org.example.marketing.repository.board.AdvertisementImageDslRepository
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service

@Service
class AdvertisementImageDslService(
    private val advertisementImageDslRepository: AdvertisementImageDslRepository
) {
    fun setImageAdvertisementIdByDraftId(targetDaftId: Long) {
        return transaction {
            advertisementImageDslRepository.setImageAdvertisementIdByDraftId(targetDaftId)
        }
    }

    fun withdrawDraft(advertiserId: Long, targetDaftId: Long) {
        return transaction {
            advertisementImageDslRepository.deleteDraftAndImagesByDraftId(targetDaftId, advertiserId)
        }
    }

    fun deleteAdvertisement(advertisementId: Long) {
        transaction {
            advertisementImageDslRepository.deleteAdvertisement(advertisementId)
        }
    }
}