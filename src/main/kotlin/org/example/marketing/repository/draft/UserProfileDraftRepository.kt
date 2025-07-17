package org.example.marketing.repository.draft

import org.example.marketing.dao.UserProfileDraftEntity
import org.example.marketing.dto.user.request.IssueUserProfileDraft
import org.example.marketing.enums.DraftStatus
import org.example.marketing.exception.NotFoundUserProfileDraftEntityException
import org.example.marketing.table.UserProfileDraftsTable
import org.jetbrains.exposed.sql.update
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.UUID

@Component
class UserProfileDraftRepository {

    fun save(saveUserProfileDraft: IssueUserProfileDraft): UserProfileDraftEntity {
        val newEntity = UserProfileDraftEntity.new {
            userId = saveUserProfileDraft.userId
            userType = saveUserProfileDraft.userType
            expiredAt = Instant.now().plus(24, ChronoUnit.HOURS).epochSecond
            draftStatus = DraftStatus.DRAFT
        }

        return newEntity
    }

    fun deleteById(targetId: UUID) {
        val deletedRow = UserProfileDraftsTable.update({ UserProfileDraftsTable.id eq targetId }) {
            it[draftStatus] = DraftStatus.DELETED
        }

        if (deletedRow == 0) {
            throw NotFoundUserProfileDraftEntityException("userProfileDraftRepo - delete By Id")
        }
    }

    fun findById(targetId: UUID): UserProfileDraftEntity? {
        val targetEntity = UserProfileDraftEntity.findById(targetId)

        return targetEntity
    }
}