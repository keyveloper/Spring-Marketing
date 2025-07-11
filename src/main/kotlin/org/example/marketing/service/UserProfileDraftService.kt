package org.example.marketing.service

import org.example.marketing.domain.user.UserProfileDraft
import org.example.marketing.dto.user.request.IssueUserProfileDraft
import org.example.marketing.enums.DraftStatus
import org.example.marketing.enums.UserType
import org.example.marketing.exception.NotFoundUserProfileDraftEntityException
import org.example.marketing.repository.draft.UserProfileDraftRepository
import org.example.marketing.table.UserProfileDraftsTable
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserProfileDraftService(
    private val userProfileDraftRepository: UserProfileDraftRepository
) {
    fun issueDraft(userId: UUID, userType: UserType): UserProfileDraft {
        return transaction {
            val newEntity = userProfileDraftRepository.save(
                IssueUserProfileDraft.of(userId, userType)
            )

            UserProfileDraft.of(newEntity)
        }
    }

    fun findById(targetId: UUID): UserProfileDraft {
        return transaction {
            val targetEntity = userProfileDraftRepository.findById(targetId)
            if (targetEntity == null) {
                throw NotFoundUserProfileDraftEntityException("userProfileDraftSvc-exist")
            } else UserProfileDraft.of(targetEntity)
        }
    }

    fun changeStatusById(targetId: UUID, status: DraftStatus): Int {
        val changedRow = UserProfileDraftsTable.update({ UserProfileDraftsTable.id eq targetId }) {
            it[draftStatus] = status
        }

        return changedRow
    }
}