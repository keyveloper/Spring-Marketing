package org.example.marketing.service

import org.example.marketing.repository.board.AdvertisementDslRepository
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service

@Service
class AdvertisementDslService(
    private val advertisementDslRepository: AdvertisementDslRepository
) {
    fun setImageAdvertisementIdByDraftId(targetDaftId: Long) {
        return transaction {
            advertisementDslRepository.setImageAdvertisementIdByDraftId(targetDaftId)
        }
    }

    fun withdrawDraft(advertiserId: Long, targetDaftId: Long) {
        return transaction {
            advertisementDslRepository.deleteDraftAndImagesByDraftId(targetDaftId, advertiserId)
        }
    }

    fun deleteAdvertisement(advertisementId: Long) {
        transaction {
            advertisementDslRepository.deleteAdvertisement(advertisementId)
        }
    }
}