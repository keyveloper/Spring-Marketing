package org.example.marketing.service

import org.example.marketing.domain.board.AdvertisementDraft
import org.example.marketing.dto.board.request.IssueAdvertisementDraft
import org.example.marketing.enums.DraftStatus
import org.example.marketing.exception.NotFoundAdDraftEntityException
import org.example.marketing.repository.board.AdvertisementDraftRepository
import org.example.marketing.table.AdvertisementDraftsTable
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.springframework.stereotype.Service
import java.util.*

@Service
class AdvertisementDraftService(
    private val advertisementDraftRepository: AdvertisementDraftRepository
) {
    fun issueDraft(advertiserId: UUID): AdvertisementDraft {
        return transaction {
            val newEntity = advertisementDraftRepository.save(
                IssueAdvertisementDraft.of(advertiserId)
            )

            AdvertisementDraft.of(newEntity)
        }
    }

    fun findById(targetId: UUID): AdvertisementDraft {
        return transaction {
            val targetEntity = advertisementDraftRepository.findById(targetId)
            if (targetEntity == null) {
                throw NotFoundAdDraftEntityException("adDraftSvc-exist")
            } else AdvertisementDraft.of(targetEntity)
        }
    }

    fun changeStatusById(targetId: UUID, status: DraftStatus): Int {
        val changedRow = AdvertisementDraftsTable.update( {AdvertisementDraftsTable.id eq targetId} ) {
            it[draftStatus] = status
        }

        return changedRow
    }
}