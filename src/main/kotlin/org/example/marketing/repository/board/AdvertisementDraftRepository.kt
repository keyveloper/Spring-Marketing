package org.example.marketing.repository.board

import org.example.marketing.dao.board.AdvertisementDraftEntity
import org.example.marketing.dto.board.request.IssueAdvertisementDraft
import org.example.marketing.enums.DraftStatus
import org.example.marketing.exception.NotFoundAdDraftEntityException
import org.example.marketing.table.AdvertisementDraftsTable
import org.jetbrains.exposed.sql.update
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.temporal.ChronoUnit

@Component
class AdvertisementDraftRepository {

    fun save(saveAdDraft: IssueAdvertisementDraft): AdvertisementDraftEntity {
        val newEntity = AdvertisementDraftEntity.new {
            advertiserId = saveAdDraft.advertiserId
            expiredAt = Instant.now().plus(24, ChronoUnit.HOURS).epochSecond
            draftStatus = DraftStatus.DRAFT
        }

        return newEntity
    }

    fun deleteById(targetId: Long) {
        val deletedRow = AdvertisementDraftsTable.update({ AdvertisementDraftsTable.id eq targetId }) {
            it[draftStatus] = DraftStatus.DELETED
        }

        if (deletedRow== 0) {
            throw NotFoundAdDraftEntityException("adDraftRepo - delete By Id")
        }
    }

    fun findById(targetId: Long): AdvertisementDraftEntity? {
        val targetEntity = AdvertisementDraftEntity.findById(targetId)

        return targetEntity
    }
}